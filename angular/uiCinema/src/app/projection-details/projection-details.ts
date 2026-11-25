import { Component, inject } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ProjectionService } from '../service/projection-service';

@Component({
  imports: [],
  selector: 'app-projection-details',
  styleUrl: './projection-details.css',
  templateUrl: './projection-details.html',
})
export class ProjectionDetails {
  route: ActivatedRoute = inject(ActivatedRoute);
  projection_service: ProjectionService = inject(ProjectionService);

  readonly baseApiUrl: string = "http://www.omdbapi.com/?&apikey=ab75fbe1&t=";


  //img_path: string;
  //title: string;

  projectionId: number = -1;
  constructor(){
    this.projectionId = Number(this.route.snapshot.params['id']);
    try{
      //this.fetchPoster(this.);
    }catch(e){
      //this.img_path = "/cat.svg";
    }
  }

  /*
  private async fetchPoster(title: string){
    this.img_path = await fetch(this.baseApiUrl + title).then(res => res.json())
      .then(function(data){
        console.log(data)
        return data.Poster;
      });

  }
      */
}