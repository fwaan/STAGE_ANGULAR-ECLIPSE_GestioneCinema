import { Component, inject, OnInit, signal } from '@angular/core';
import { CurrencyPipe, DatePipe } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatDialog } from '@angular/material/dialog';
import { FilmModel } from '../model/film';
import { ProjectionModel } from '../model/projection';
import { FilmService } from '../service/film-service';
import { ProjectionService } from '../service/projection-service';
import { FilmNavBar } from '../film-details/film-nav-bar/film-nav-bar';
import { FilmFormDialog } from '../film-form-dialog/film-form-dialog';
 
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
 
  films = signal<FilmModel[]>([]);
  selected = signal<FilmModel | null>(null);
  projections = signal<ProjectionModel[]>([]);
  loading = signal(true);
  loadingProjections = signal(false);
  error = signal<string | null>(null);
 
  ngOnInit(): void {
    this.caricaFilm();
  }
 
  caricaFilm(): void {
    this.filmService.getAll().subscribe({
      next: (list) => {
        this.films.set(list);
        this.loading.set(false);
      },
      error: () => {
        this.error.set('Impossibile caricare i film');
        this.loading.set(false);
      },
    });
  }
 
  seleziona(film: FilmModel): void {
    this.selected.set(film);
    this.projections.set([]);
    this.loadingProjections.set(true);
 
    this.projectionService.getByMovie(film.id).subscribe({
      next: (list) => {
        if (this.selected()?.id === film.id) {
          this.projections.set(list);
          this.loadingProjections.set(false);
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
 
  nuovoFilm(): void {
    this.dialog
      .open(FilmFormDialog, { width: '480px', maxWidth: '95vw' })
      .afterClosed()
      .subscribe((salvato) => {
        if (salvato) this.caricaFilm();
      });
  }
 
  modificaFilm(film: FilmModel): void {}
  eliminaFilm(film: FilmModel): void {}
 
  nuovaProiezione(): void {}
  modificaProiezione(p: ProjectionModel): void {}
  eliminaProiezione(p: ProjectionModel): void {}
}