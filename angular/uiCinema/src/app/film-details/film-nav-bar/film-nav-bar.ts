import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatDialog } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatToolbarModule } from '@angular/material/toolbar';
import { RouterLink } from '@angular/router';
import { AuthDialog } from '../../auth-dialog/auth-dialog';
import { AuthService } from '../../service/auth-service';

@Component({
  imports: [MatToolbarModule, MatIconModule, MatFormFieldModule, ReactiveFormsModule, MatInputModule, MatButtonModule, CommonModule, RouterLink],
  selector: 'app-film-nav-bar',
  styleUrl: './film-nav-bar.css',
  templateUrl: './film-nav-bar.html',
})
export class FilmNavBar {
  private dialog = inject(MatDialog);
  protected auth: AuthService = inject(AuthService);

  openAuth(): void {
    this.dialog.open(AuthDialog, { width: '440px', maxWidth: '95vw' });
  }
}