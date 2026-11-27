import { inject, Service } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export type PaymentMethod = 'CASH' | 'CREDIT_CARD' | 'DEBIT_CARD' | 'PAYPAL';

@Service()
export class PurchaseService {

  private client: HttpClient = inject(HttpClient);
  private readonly baseApi: string = 'http://localhost:8080/purchase';

  buy(customerId: number, hall: string, projectionTime: string, pm: PaymentMethod): Observable<string> {
    return this.client.post(
      this.baseApi + '/buy',
      {
        customer_id: customerId,
        prj: { hall, projection_time: projectionTime },
        omage: false,
        fast_track: false,
        pm,
      },
      { responseType: 'text' },
    );
  }
}
