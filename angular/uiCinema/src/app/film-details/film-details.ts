import { Component, inject, input, Signal, signal } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ProjectionService } from '../service/projection-service';
import { FilmModel } from '../model/film';
import { map, Observable, switchMap } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { FilmService } from '../service/film-service';
import { ProjectionModel } from '../model/projection';
import { AsyncPipe, DatePipe } from '@angular/common';
import { AvailableProjections } from '../available-projections/available-projections';
import { MatIconModule } from '@angular/material/icon';
import { MatCardModule } from '@angular/material/card';
import { FilmNavBar } from './film-nav-bar/film-nav-bar';


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
  projections: Observable<ProjectionModel[]>;
  omdbResponse: Observable<OmdbResponse> | undefined;
  availableSeats: Observable<Number>[];

  private readonly baseApi = "http://www.omdbapi.com/?&apikey=ab75fbe1";

  constructor(){
    this.filmTitle = String(this.route.snapshot.params['title']);
    this.film = this.filmService.findByTitle(this.filmTitle);
    this.projections = this.projectionService.getByMovieTitle(this.filmTitle); 

    this.omdbResponse = this.fetcher.get<OmdbResponse>(this.baseApi + "&t=" + this.filmTitle);
    
    this.availableSeats = [];
    this.projections.forEach((p) => {
      p.forEach((pp) => this.availableSeats.push(this.projectionService.availableSeats(pp)))
    });
    
    
    //Cosa orrenda ma l'ho imparata solo ora
    //const vado_di_fretta = toSignal(this.film);
    //Qua guardano solo Undefinend perché questa risposta non viene aspettata 
    //this.omdbResponse = this.fetcher.get<OmdbResponse>(this.baseApi + "&t=" + vado_di_fretta()?.title);
    
  }

  getPostiDisponibili(i: number): Observable<Number>{
    return this.availableSeats[i];
    //return 10;
  }

  acquista(p: ProjectionModel){

  }
  //Chiamata inutile
  

}
