import { Component, inject, OnInit, signal, effect } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../service/auth-service';
import { CurrencyPipe, DatePipe } from '@angular/common';
import { HttpErrorResponse } from '@angular/common/http';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { FilmModel } from '../model/film';
import { ProjectionModel } from '../model/projection';
import { FilmService } from '../service/film-service';
import { ProjectionService } from '../service/projection-service';
import { FilmNavBar } from '../film-details/film-nav-bar/film-nav-bar';
import { FilmFormDialog } from '../film-form-dialog/film-form-dialog';
import { ProjectionFormDialog, ProjectionDialogData } from '../projection-form-dialog/projection-form-dialog';
import { ConfirmDialog, ConfirmData } from '../confirm-dialog/confirm-dialog';

@Component({
  imports: [CurrencyPipe, DatePipe, MatButtonModule, MatIconModule, FilmNavBar],
  selector: 'app-admin-home',
  styleUrl: './admin-home.css',
  templateUrl: './admin-home.html',
})
export class AdminHome implements OnInit {

  private filmService: FilmService = inject(FilmService);
  private projectionService: ProjectionService = inject(ProjectionService);
  private dialog = inject(MatDialog);
  private snackBar = inject(MatSnackBar);
  private auth = inject(AuthService);
  private router = inject(Router);


  films = signal<FilmModel[]>([]);
  selected = signal<FilmModel | null>(null);
  projections = signal<ProjectionModel[]>([]);
  loading = signal(true);
  loadingProjections = signal(false);
  error = signal<string | null>(null);
  postiLiberi = signal<Record<string, number>>({});

  constructor() {
    effect(() => {
      if (!this.auth.isAdmin()) {
        this.router.navigate(['/']);
      }
    });
  }

  ngOnInit(): void {
    this.caricaFilm();
  }

  caricaFilm(): void {
    this.filmService.getAll().subscribe({
      next: (list) => {
        this.films.set(list);
        this.loading.set(false);
        const corrente = this.selected();
        if (corrente) {
          const aggiornato = list.find((f) => f.id === corrente.id) ?? null;
          this.selected.set(aggiornato);
          if (!aggiornato) this.projections.set([]);
        }
      },
      error: () => {
        this.error.set('Impossibile caricare i film');
        this.loading.set(false);
      },
    });
  }

  private caricaProiezioni(film: FilmModel): void {
    this.loadingProjections.set(true);
    this.projectionService.getByMovie(film.id).subscribe({
      next: (list) => {
        if (this.selected()?.id === film.id) {
          this.projections.set(list);
          this.loadingProjections.set(false);
          this.caricaPosti(list);
        }
      },
      error: () => {
        if (this.selected()?.id === film.id) {
          this.projections.set([]);
          this.loadingProjections.set(false);
        }
      },
    });
  }

  private chiave(p: ProjectionModel): string {
    return p.hall + '|' + p.projection_time;
  }

  private caricaPosti(list: ProjectionModel[]): void {
    this.postiLiberi.set({});
    list.forEach((p) => {
      this.projectionService.availableSeats(p).subscribe({
        next: (n) => this.postiLiberi.update((m) => ({ ...m, [this.chiave(p)]: Number(n) })),
        error: () => {},
      });
    });
  }

  posti(p: ProjectionModel): string {
    const liberi = this.postiLiberi()[this.chiave(p)];
    return (liberi === undefined ? '…' : liberi) + '/' + p.capacity;
  }

  seleziona(film: FilmModel): void {
    this.selected.set(film);
    this.projections.set([]);
    this.caricaProiezioni(film);
  }

  private avviso(messaggio: string): void {
    this.snackBar.open(messaggio, 'OK', { duration: 4000 });
  }

  private conferma(dati: ConfirmData, quando: () => void): void {
    this.dialog
      .open(ConfirmDialog, { width: '420px', maxWidth: '95vw', data: dati })
      .afterClosed()
      .subscribe((ok) => {
        if (ok) quando();
      });
  }

  nuovoFilm(): void {
    this.apriFormFilm(null);
  }

  modificaFilm(film: FilmModel): void {
    this.apriFormFilm(film);
  }

  private apriFormFilm(film: FilmModel | null): void {
    this.dialog
      .open(FilmFormDialog, { width: '480px', maxWidth: '95vw', data: film })
      .afterClosed()
      .subscribe((salvato) => {
        if (salvato) this.caricaFilm();
      });
  }

  eliminaFilm(film: FilmModel): void {
    this.conferma(
      { title: 'Elimina film', message: `Sei sicuro di voler eliminare "${film.title}"?`, confirm: 'Elimina' },
      () => {
        this.filmService.remove(film.id).subscribe({
          next: () => {
            if (this.selected()?.id === film.id) {
              this.selected.set(null);
              this.projections.set([]);
            }
            this.caricaFilm();
            this.avviso('Film eliminato');
          },
          error: (err: HttpErrorResponse) =>
            this.avviso(
              err.status === 409
                ? 'Impossibile eliminare: il film ha delle proiezioni'
                : 'Eliminazione non riuscita',
            ),
        });
      },
    );
  }

  nuovaProiezione(): void {
    this.apriFormProiezione(null);
  }

  modificaProiezione(p: ProjectionModel): void {
    this.apriFormProiezione(p);
  }

  private apriFormProiezione(projection: ProjectionModel | null): void {
    const film = this.selected();
    if (!film) return;
    const data: ProjectionDialogData = { film, projection };
    this.dialog
      .open(ProjectionFormDialog, { width: '480px', maxWidth: '95vw', data })
      .afterClosed()
      .subscribe((salvato) => {
        if (salvato) this.caricaProiezioni(film);
      });
  }

  eliminaProiezione(p: ProjectionModel): void {
    const film = this.selected();
    if (!film) return;
    this.conferma(
      {
        title: 'Elimina proiezione',
        message: `Sei sicuro di voler eliminare la proiezione in sala ${p.hall}?`,
        confirm: 'Elimina',
      },
      () => {
        this.projectionService.remove(p.hall, String(p.projection_time)).subscribe({
          next: () => {
            this.caricaProiezioni(film);
            this.avviso('Proiezione eliminata');
          },
          error: (err: HttpErrorResponse) =>
            this.avviso(
              err.status === 409
                ? 'Impossibile eliminare: ci sono biglietti venduti'
                : 'Eliminazione non riuscita',
            ),
        });
      },
    );
  }
}
