import { input, Component } from '@angular/core';
import { ProjectionModel } from '../model/projection';
import { RouterLink } from '@angular/router';

@Component({
  imports: [RouterLink],
  selector: 'app-projection',
  styleUrl: './projection.css',
  templateUrl: './projection.html',
})
export class Projection {

  projection = input.required<ProjectionModel>();

}
