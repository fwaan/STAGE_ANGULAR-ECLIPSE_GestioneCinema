import { Component, inject, signal } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { HttpErrorResponse } from '@angular/common/http';
import { MatButtonModule } from '@angular/material/button';
import { MAT_DIALOG_DATA, MatDialogModule, MatDialogRef } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { FilmModel } from '../model/film';
import { ProjectionModel } from '../model/projection';
import { ProjectionService } from '../service/projection-service';

export interface ProjectionDialogData {
  film: FilmModel;
  projection: ProjectionModel | null;
}

@Component({
  selector: 'app-projection-form-dialog',
  imports: [ReactiveFormsModule, MatDialogModule, MatFormFieldModule, MatInputModule, MatButtonModule],
  templateUrl: './projection-form-dialog.html',
  styleUrl: './projection-form-dialog.css',
})
export class ProjectionFormDialog {
  private fb = inject(FormBuilder);
  private dialogRef = inject<MatDialogRef<ProjectionFormDialog>>(MatDialogRef);
  private service: ProjectionService = inject(ProjectionService);
  data = inject<ProjectionDialogData>(MAT_DIALOG_DATA);

  isEdit = this.data.projection !== null;
  loading = signal(false);
  error = signal<string | null>(null);

  form = this.fb.nonNullable.group({
    hall: [this.data.projection?.hall ?? '', Validators.required],
    projection_time: [
      this.data.projection ? String(this.data.projection.projection_time).slice(0, 16) : '',
      Validators.required,
    ],
    capacity: [this.data.projection?.capacity ?? 100, [Validators.required, Validators.min(1)]],
  });

  constructor() {
    if (this.isEdit) {
      this.form.controls.hall.disable();
      this.form.controls.projection_time.disable();
    }
  }

  onSubmit(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }
    this.error.set(null);
    this.loading.set(true);

    const v = this.form.getRawValue();

    const request = this.data.projection
      ? this.service.modify(
          this.data.projection.hall,
          String(this.data.projection.projection_time),
          v.capacity,
        )
      : this.service.insert({
          hall: v.hall,
          projection_time: v.projection_time.length === 16 ? v.projection_time + ':00' : v.projection_time,
          film: this.data.film,
          capacity: v.capacity,
        });

    request.subscribe({
      next: () => this.dialogRef.close(true),
      error: (err: HttpErrorResponse) => {
        let message = 'Salvataggio non riuscito';
        if (err.status === 403) message = 'Non hai i permessi per questa operazione';
        else if (err.status === 409)
          message = this.isEdit
            ? 'La capienza è inferiore ai biglietti già venduti'
            : "Esiste già una proiezione in quella sala a quell'ora";
        this.error.set(message);
        this.loading.set(false);
      },
    });
  }
}
