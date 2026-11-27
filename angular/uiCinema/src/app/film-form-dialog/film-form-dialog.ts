import { Component, inject, signal } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { HttpErrorResponse } from '@angular/common/http';
import { MatButtonModule } from '@angular/material/button';
import { MAT_DIALOG_DATA, MatDialogModule, MatDialogRef } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { FilmModel } from '../model/film';
import { FilmService } from '../service/film-service';

@Component({
  selector: 'app-film-form-dialog',
  imports: [ReactiveFormsModule, MatDialogModule, MatFormFieldModule, MatInputModule, MatButtonModule],
  templateUrl: './film-form-dialog.html',
  styleUrl: './film-form-dialog.css',
})
export class FilmFormDialog {
  private fb = inject(FormBuilder);
  private dialogRef = inject<MatDialogRef<FilmFormDialog>>(MatDialogRef);
  private filmService: FilmService = inject(FilmService);
  private data: FilmModel | null = inject<FilmModel | null>(MAT_DIALOG_DATA, { optional: true }) ?? null;

  isEdit = this.data !== null;
  loading = signal(false);
  error = signal<string | null>(null);

  form = this.fb.nonNullable.group({
    title: [this.data?.title ?? '', Validators.required],
    genre: [this.data?.genre ?? '', Validators.required],
    duration_in_minutes: [this.data?.duration_in_minutes ?? 90, [Validators.required, Validators.min(1)]],
    starter_price: [this.data?.starter_price ?? 8, [Validators.required, Validators.min(0)]],
  });

  onSubmit(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }
    this.error.set(null);
    this.loading.set(true);

    const value = this.form.getRawValue();
    const request = this.data
      ? this.filmService.modify(this.data.id, value)
      : this.filmService.insert(value);

    request.subscribe({
      next: () => this.dialogRef.close(true),
      error: (err: HttpErrorResponse) => {
        this.error.set(
          err.status === 403 ? 'Non hai i permessi per questa operazione' : 'Salvataggio non riuscito',
        );
        this.loading.set(false);
      },
    });
  }
}
