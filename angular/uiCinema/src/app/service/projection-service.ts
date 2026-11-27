import { inject, Service } from '@angular/core';
import { ProjectionModel } from '../model/projection';
import { FilmModel } from '../model/film';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface NewProjection {
    hall: string;
    projection_time: string;
    film: FilmModel;
    capacity: number;
}

/*interface ProjectionKey{
  hall: string,
  projection_time: Date
}*/

@Service()
export class ProjectionService {

    private readonly apiBase = "http://localhost:8080/projection";
    private fetcher: HttpClient = inject(HttpClient);


    pezzotto: ProjectionModel[] = [];

    constructor(){
        this.fetchProjections().catch(() => {});
    }

  getAll(): ProjectionModel[]{
    return this.pezzotto;
  }

  getById(hall: string, projection_time: Date): ProjectionModel | undefined{
    return this.pezzotto.find((u) => u.hall == hall && u.projection_time == projection_time);
  }

  private async fetchProjections(){
    const response = await fetch(this.apiBase + "/all");

    if(!response.ok)
        throw new Error(`HTTP error! Status: ${response.status}`);

    this.pezzotto = await (response.json()) as ProjectionModel[];
  }

  getByMovie(movie_id: Number): Observable<ProjectionModel[]>{
    return this.fetcher.get<ProjectionModel[]>(this.apiBase + "/projectionsByMovie/" + movie_id);
  }

  getByMovieTitle(movie_title: string): Observable<ProjectionModel[]>{
    return this.fetcher.get<ProjectionModel[]>(this.apiBase + "/byMovieTitle/" + movie_title);
  }

  availableSeats(projection: ProjectionModel): Observable<Number>{
    return this.fetcher.get<Number>(this.apiBase + "/seatsLeft?projection_time=" + projection.projection_time.toString() + "&hall=" + projection.hall );
  }

  insert(p: NewProjection): Observable<string> {
    return this.fetcher.post(
      this.apiBase + "/insert",
      { hall: p.hall, projection_time: p.projection_time, capacity: p.capacity, film_id: { id: p.film.id, title: p.film.title, genre: p.film.genre, duration_in_minutes: p.film.duration_in_minutes, starter_price: p.film.starter_price } },
      { responseType: 'text' }
    );
  }

  modify(hall: string, projection_time: string, capacity: number): Observable<string> {
    const params = new HttpParams().set('hall', hall).set('projection_time', projection_time).set('capacity', capacity);
    return this.fetcher.put(this.apiBase + "/modify", null, { params, responseType: 'text' });
  }

  remove(hall: string, projection_time: string): Observable<string> {
    const params = new HttpParams().set('hall', hall).set('projection_time', projection_time);
    return this.fetcher.delete(this.apiBase + "/delete", { params, responseType: 'text' });
  }

}
