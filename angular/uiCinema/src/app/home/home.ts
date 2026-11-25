import { Component, inject } from '@angular/core';
import { FilmService } from '../service/film-service';
import { FilmModel } from '../model/film';
import { Film } from '../film/film';
import { Observable } from 'rxjs';
import { AsyncPipe } from '@angular/common';
import { RouterLink } from '@angular/router';
import { HomeNavBar } from './home-nav-bar/home-nav-bar';
import { FormControl } from '@angular/forms';
import { FilterPipe } from '../pipe/filter-pipe';
import { FrancescoPipe } from '../pipe/pipe-francesco';
import { FilmPipe } from '../pipe/film-pipe';

@Component({
  imports: [Film, AsyncPipe, RouterLink, HomeNavBar, FilmPipe, FrancescoPipe],
  selector: 'app-home',
  styleUrl: './home.css',
  templateUrl: './home.html',
})


export class Home {
  
  service: FilmService = inject(FilmService);
  films: Observable<FilmModel[]>;
  filmsFilter: Observable<FilmModel[]>;
  filtroLetto: string | string = '';

  

  constructor(){
    this.films = this.service.getAll();
    this.filmsFilter = this.films;
  }

  filter(event: any){
    this.filtroLetto = event;
  }

}
