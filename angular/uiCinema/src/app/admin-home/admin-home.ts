import { Component, inject, OnInit } from '@angular/core';
import { CurrencyPipe, DatePipe } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { FilmModel } from '../model/film';
import { ProjectionModel } from '../model/projection';
// questa qua non so se sia la navbar giusta
// import { FilmNavBar } from '../film-details/film-nav-bar/film-nav-bar';

@Component({
  imports: [CurrencyPipe, DatePipe, MatButtonModule, MatIconModule],
  selector: 'app-admin-home',
  styleUrl: './admin-home.css',
  templateUrl: './admin-home.html',
})



export class AdminHome {

  films: FilmModel[] = [];
  selected: FilmModel | null | undefined;
  projections: ProjectionModel[] = [];

  seleziona(film: FilmModel): void {
    this.selected = film;
    //chiamata al backend per riempire this.projections che non ricordo come l'hai fatto
    this.projections = [];
  }

  nuovoFilm(): void {}
  modificaFilm(film: FilmModel): void {}
  eliminaFilm(film: FilmModel): void {}

  nuovaProiezione(): void {}
  modificaProiezione(p: ProjectionModel): void{}
  eliminaProiezione(p: ProjectionModel): void{}




  /*
  service: CustomerService = inject(CustomerService);
  //customerList: Observable<CustomerModel[]>;
  filteredList!: CustomerModel[];
  filteringForm!: FormGroup;

  constructor(){
    
    
    
  }

  ngOnInit(){
    this.service.getAll().subscribe((list) => {
      this.filteredList = list
      console.log(this.filteredList);
    });

    this.filteringForm = new FormGroup({
    name: new FormControl(''),
    surname: new FormControl(''),
    disability: new FormControl(false),
    age: new FormControl(0)
  });
  }
  */

  
}

