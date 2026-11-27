import { Component, inject, input, Signal, signal } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ProjectionService } from '../service/projection-service';
import { FilmModel } from '../model/film';
import { map, Observable, shareReplay, switchMap } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { FilmService } from '../service/film-service';
import { ProjectionModel } from '../model/projection';
import { AsyncPipe, DatePipe } from '@angular/common';
import { AvailableProjections } from '../available-projections/available-projections';
import { MatIconModule } from '@angular/material/icon';
import { MatCardModule } from '@angular/material/card';
import { FilmNavBar } from './film-nav-bar/film-nav-bar';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { AuthService } from '../service/auth-service';
import { AuthDialog } from '../auth-dialog/auth-dialog';
import { PurchaseDialog } from '../purchase-dialog/purchase-dialog';


@Component({
  imports: [AsyncPipe, AvailableProjections, MatIconModule, MatCardModule, DatePipe, FilmNavBar],
  selector: 'app-film-details',
  styleUrl: './film-details.css',
  templateUrl: './film-details.html',
})
export class FilmDetails {
  route: ActivatedRoute = inject(ActivatedRoute);
  fetcher: HttpClient = inject(HttpClient);
  projectionService: ProjectionService = inject(ProjectionService);
  filmService: FilmService = inject(FilmService);
  filmTitle: string = "";
  film: Observable<FilmModel>;
  projections!: Observable<ProjectionModel[]>;
  private dialog = inject(MatDialog);
  private snackBar = inject(MatSnackBar);
  private auth = inject(AuthService);
  omdbResponse: Observable<OmdbResponse> | undefined;
  availableSeats: Observable<Number>[] = [];

  private readonly baseApi = "http://www.omdbapi.com/?&apikey=ab75fbe1";

  constructor(){
    this.filmTitle = String(this.route.snapshot.params['title']);
    this.film = this.filmService.findByTitle(this.filmTitle);
    this.caricaProiezioni();

    this.omdbResponse = this.fetcher.get<OmdbResponse>(this.baseApi + "&t=" + this.filmTitle);
    
    
    
    //Cosa orrenda ma l'ho imparata solo ora
    //const vado_di_fretta = toSignal(this.film);
    //Qua guardano solo Undefinend perché questa risposta non viene aspettata 
    //this.omdbResponse = this.fetcher.get<OmdbResponse>(this.baseApi + "&t=" + vado_di_fretta()?.title);
    
  }

  getPostiDisponibili(i: number): Observable<Number>{
    return this.availableSeats[i];
    //return 10;
  }

  private caricaProiezioni(): void {
    const proiezioni$ = this.projectionService.getByMovieTitle(this.filmTitle).pipe(shareReplay(1));
    this.projections = proiezioni$;
    this.availableSeats = [];
    proiezioni$.subscribe((list) => {
      this.availableSeats = list.map((pp) => this.projectionService.availableSeats(pp));
    });
  }

  acquista(p: ProjectionModel){
    if (!this.auth.isLoggedIn()) {
      this.dialog
        .open(AuthDialog, { width: '440px', maxWidth: '95vw' })
        .afterClosed()
        .subscribe((ok) => {
          if (ok) this.apriAcquisto(p);
        });
      return;
    }
    this.apriAcquisto(p);
  }

  private apriAcquisto(p: ProjectionModel): void {
    this.dialog
      .open(PurchaseDialog, {
        width: '440px',
        maxWidth: '95vw',
        data: { title: this.filmTitle, projection: p },
      })
      .afterClosed()
      .subscribe((acquistato) => {
        if (acquistato) {
          window.location.reload();
        }
      });
  }
  //Chiamata inutile
  

}
