import { Component, inject, input } from '@angular/core';
import { RouterLink, ɵEmptyOutletComponent } from '@angular/router';
import { FilmModel } from '../model/film';
import { HttpClient } from '@angular/common/http';
import { map, Observable, switchMap } from 'rxjs';
import { toObservable } from '@angular/core/rxjs-interop';
import { AsyncPipe } from '@angular/common';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';


@Component({
  imports: [RouterLink, AsyncPipe, MatCardModule, MatIconModule],
  selector: 'app-film',
  styleUrl: './film.css',
  templateUrl: './film.html',
})
export class Film {

  film = input.required<FilmModel>();

  private fetcher: HttpClient = inject(HttpClient);
  private readonly baseApi = "http://www.omdbapi.com/?&apikey=ab75fbe1";

  //this.film è il segnale dell'observer ==> al cambiare del film si aggiorna posterUrl
  //nella maniera definita dalla pipe
  posterUrl = toObservable(this.film).pipe(
    switchMap((f) =>
      this.fetcher.get<OmdbResponse>(this.baseApi + "&t=" + this.film().title)
    ), map((res) => (res.Response === 'True' && res.Poster !== 'N/A' ? res.Poster : "cat.svg"))
  );

  
}
