import { CommonModule } from '@angular/common';
import { Component, computed, EventEmitter, inject, Output } from '@angular/core';
import { FormControl, ReactiveFormsModule } from '@angular/forms';
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
  selector: 'app-home-nav-bar',
  styleUrl: './home-nav-bar.css',
  templateUrl: './home-nav-bar.html',
})
export class HomeNavBar {
  private dialog = inject(MatDialog);
  protected auth: AuthService = inject(AuthService);

  @Output() titleSearched = new EventEmitter();
  searchCtrl: FormControl = new FormControl('');

  onSearch(): void {
    this.titleSearched.emit(this.searchCtrl.value);
  }

  openAuth(): void {
    this.dialog.open(AuthDialog, { width: '440px', maxWidth: '95vw' });
  }
}