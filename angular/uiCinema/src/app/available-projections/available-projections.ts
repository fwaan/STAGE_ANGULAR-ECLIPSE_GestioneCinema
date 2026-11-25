import { Component, input } from '@angular/core';
import { ProjectionModel } from '../model/projection';

@Component({
  imports: [],
  selector: 'app-available-projections',
  styleUrl: './available-projections.css',
  templateUrl: './available-projections.html',
})
export class AvailableProjections {
  projection = input.required<ProjectionModel>();
}
