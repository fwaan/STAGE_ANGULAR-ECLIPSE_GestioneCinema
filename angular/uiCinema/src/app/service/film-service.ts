import { inject, Service } from '@angular/core';
import { FilmModel } from '../model/film';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Service()
export class FilmService {

    private client: HttpClient = inject(HttpClient);
    private readonly baseApi: string = "http://localhost:8080/film";

    getAll(): Observable<FilmModel[]>{
        return this.client.get<FilmModel[]>(this.baseApi + "/all");
    }

    findById(movie_id: Number): Observable<FilmModel>{
        return this.client.get<FilmModel>(this.baseApi + "/find/" + movie_id);
    }

    findByTitle(movie_title: string): Observable<FilmModel>{
        return this.client.get<FilmModel>(this.baseApi + "/byName/" + movie_title);
    }
}
