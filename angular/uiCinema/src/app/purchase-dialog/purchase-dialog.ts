import { Component, inject, signal } from '@angular/core';
import { DatePipe } from '@angular/common';
import { HttpErrorResponse } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MAT_DIALOG_DATA, MatDialogModule, MatDialogRef } from '@angular/material/dialog';
import { MatRadioModule } from '@angular/material/radio';
import { ProjectionModel } from '../model/projection';
import { AuthService } from '../service/auth-service';
import { PaymentMethod, PurchaseService } from '../service/purchase-service';

export interface PurchaseDialogData {
  title: string;
  projection: ProjectionModel;
}

@Component({
  selector: 'app-purchase-dialog',
  imports: [FormsModule, DatePipe, MatDialogModule, MatButtonModule, MatRadioModule],
  template: `
    <h2 mat-dialog-title>Acquista biglietto</h2>

    <mat-dialog-content class="purchase">
      <p class="summary">
        <strong>{{ data.title }}</strong><br />
        Sala {{ data.projection.hall }} ·
        {{ data.projection.projection_time | date: 'dd/MM/yyyy HH:mm' }}
      </p>

      <p class="label">Metodo di pagamento</p>
      <mat-radio-group class="methods" [(ngModel)]="method">
        <mat-radio-button value="CREDIT_CARD">Carta di credito</mat-radio-button>
        <mat-radio-button value="DEBIT_CARD">Carta di debito</mat-radio-button>
        <mat-radio-button value="PAYPAL">PayPal</mat-radio-button>
        <mat-radio-button value="CASH">Contanti</mat-radio-button>
      </mat-radio-group>

      @if (error()) {
        <p class="error">{{ error() }}</p>
      }
    </mat-dialog-content>

    <mat-dialog-actions align="end">
      <button mat-button type="button" mat-dialog-close>Annulla</button>
      <button mat-flat-button type="button" [disabled]="loading()" (click)="conferma()">
        {{ loading() ? 'Acquisto in corso…' : 'Conferma acquisto' }}
      </button>
    </mat-dialog-actions>
  `,
  styles: `
    .purchase { display: flex; flex-direction: column; gap: 8px; }
    .summary { margin: 0 0 8px; }
    .label { margin: 0; font-weight: 600; }
    .methods { display: flex; flex-direction: column; }
    .error { margin: 0; color: #c0392b; font-size: 0.9rem; }
  `,
})
export class PurchaseDialog {
  private dialogRef = inject<MatDialogRef<PurchaseDialog>>(MatDialogRef);
  private purchase: PurchaseService = inject(PurchaseService);
  private auth: AuthService = inject(AuthService);
  data = inject<PurchaseDialogData>(MAT_DIALOG_DATA);

  method: PaymentMethod = 'CREDIT_CARD';
  loading = signal(false);
  error = signal<string | null>(null);

  conferma(): void {
    const userId = this.auth.userId();
    if (userId === null) {
      this.error.set('Utente non riconosciuto: esci e accedi di nuovo');
      return;
    }
    this.error.set(null);
    this.loading.set(true);

    this.purchase
      .buy(userId, this.data.projection.hall, String(this.data.projection.projection_time), this.method)
      .subscribe({
        next: (risposta) => {
          if (!risposta) {
            this.error.set('Acquisto non riuscito (posti esauriti?)');
            this.loading.set(false);
            return;
          }
          this.dialogRef.close(true);
        },
        error: (err: HttpErrorResponse) => {
          this.error.set(
            err.status === 403 ? 'Per acquistare devi accedere come cliente' : 'Acquisto non riuscito',
          );
          this.loading.set(false);
        },
      });
  }
}
