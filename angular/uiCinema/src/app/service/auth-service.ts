import { computed, inject, Service, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';

export interface RegisterData {
  nome: string;
  cognome: string;
  dob: string;
  email: string;
  password: string;
}

@Service()
export class AuthService {

  private client: HttpClient = inject(HttpClient);

  private readonly baseApi: string = 'http://localhost:8080/auth';
  private readonly tokenKey = 'cinegatto_token';

  token = signal<string | null>(this.readToken());
  isLoggedIn = computed(() => this.token() !== null);

  userEmail = computed(() => {
    const t = this.token();
    if (!t) return null;
    try {
      const payload = JSON.parse(atob(t.split('.')[1].replace(/-/g, '+').replace(/_/g, '/')));
      return (payload.sub as string) ?? null;
    } catch {
      return null;
    }
  });

  isAdmin = computed(() => {
    const p = this.decodePayload(this.token());
    return p?.admin === true || p?.admin === 'true';
  });


  userId = computed(() => {
    const p = this.decodePayload(this.token());
    return typeof p?.uid === 'number' ? (p.uid as number) : null;
  });

  login(email: string, password: string): Observable<string> {
    return this.client
      .post(this.baseApi + '/generateToken', { username: email, password }, { responseType: 'text' })
      .pipe(tap((token) => this.saveToken(token)));
  }

  register(data: RegisterData): Observable<string> {
    return this.client.post(this.baseApi + '/register', data, { responseType: 'text' });
  }

  logout(): void {
    this.saveToken(null);
  }

  private saveToken(token: string | null): void {
    this.token.set(token);
    try {
      if (token) localStorage.setItem(this.tokenKey, token);
      else localStorage.removeItem(this.tokenKey);
    } catch {
    }
  }

  private readToken(): string | null {
    try {
      const t = localStorage.getItem(this.tokenKey);
      if (!t) return null;
      const payload = JSON.parse(atob(t.split('.')[1].replace(/-/g, '+').replace(/_/g, '/')));
      if (payload.exp && payload.exp * 1000 < Date.now()) {
        localStorage.removeItem(this.tokenKey);
        return null;
      }
      return t;
    } catch {
      return null;
    }
  }

  private decodePayload(token: string | null): any {
    if (!token) return null;
    try {
      const part = token.split('.')[1].replace(/-/g, '+').replace(/_/g, '/');
      const padded = part + '='.repeat((4 - (part.length % 4)) % 4);
      return JSON.parse(atob(padded));
    } catch (e) {
      console.error('Token non leggibile', e);
      return null;
    }
  }
}
