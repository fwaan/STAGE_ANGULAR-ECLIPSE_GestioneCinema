import { Component, inject, signal } from '@angular/core';
import {
  AbstractControl,
  FormBuilder,
  ReactiveFormsModule,
  ValidationErrors,
  Validators,
} from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatDialogModule, MatDialogRef } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatTabsModule } from '@angular/material/tabs';
import { HttpErrorResponse } from '@angular/common/http';
import { switchMap } from 'rxjs';
import { AuthService } from '../service/auth-service';

function passwordsMatch(group: AbstractControl): ValidationErrors | null {
  const password = group.get('password')?.value;
  const conferma = group.get('conferma')?.value;
  return password === conferma ? null : { mismatch: true };
}

@Component({
  selector: 'app-auth-dialog',
  imports: [
    ReactiveFormsModule,
    MatDialogModule,
    MatTabsModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatIconModule,
  ],
  templateUrl: './auth-dialog.html',
  styleUrl: './auth-dialog.css',
})
export class AuthDialog {
  private fb = inject(FormBuilder);
  private dialogRef = inject<MatDialogRef<AuthDialog>>(MatDialogRef);
  private auth: AuthService = inject(AuthService);

  hidePassword = signal(true);
  loading = signal(false);
  error = signal<string | null>(null);

  loginForm = this.fb.nonNullable.group({
    email: ['', [Validators.required, Validators.email]],
    password: ['', Validators.required],
  });

  registerForm = this.fb.nonNullable.group(
    {
      nome: ['', Validators.required],
      cognome: ['', Validators.required],
      dob: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(8)]],
      conferma: ['', Validators.required],
    },
    { validators: passwordsMatch },
  );

  onLogin(): void {
    if (this.loginForm.invalid) {
      this.loginForm.markAllAsTouched();
      return;
    }
    this.error.set(null);
    this.loading.set(true);

    const { email, password } = this.loginForm.getRawValue();

    this.auth.login(email, password).subscribe({
      next: () => this.dialogRef.close(true),
      error: () => {
        this.error.set('Email o password non corrette');
        this.loading.set(false);
      },
    });
  }

  onRegister(): void {
    if (this.registerForm.invalid) {
      this.registerForm.markAllAsTouched();
      return;
    }
    this.error.set(null);
    this.loading.set(true);

    const { nome, cognome, dob, email, password } = this.registerForm.getRawValue();

    this.auth
      .register({ nome, cognome, dob, email, password })
      .pipe(switchMap(() => this.auth.login(email, password)))
      .subscribe({
        next: () => this.dialogRef.close(true),
        error: (err: HttpErrorResponse) => {
          this.error.set(
            err.status === 409
              ? 'Esiste già un account con questa email'
              : 'Registrazione non riuscita. Riprova',
          );
          this.loading.set(false);
        },
      });
  }
}
