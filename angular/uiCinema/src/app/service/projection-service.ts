import { inject, Service } from '@angular/core';
import { ProjectionModel } from '../model/projection';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

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
        this.fetchProjections();
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
   
}
