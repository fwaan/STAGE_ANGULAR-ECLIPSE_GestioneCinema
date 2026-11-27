import { inject, Service } from '@angular/core';
import { FilmModel } from '../model/film';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface NewFilm{
    title: string;
    genre: string;
    duration_in_minutes: number;
    starter_price: number;
}

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

    insert(film: NewFilm): Observable<string> {
        return this.client.post(this.baseApi + "/insert", film, {responseType: 'text'});
    }

    modify(id: number, film: NewFilm): Observable<string> {
        return this.client.put(this.baseApi + "/modify/" + id, film, {responseType: 'text'});
    }

    remove(id: number): Observable<string> {
        return this.client.delete(this.baseApi + "/delete/" + id, {responseType: 'text'});
    }
}
