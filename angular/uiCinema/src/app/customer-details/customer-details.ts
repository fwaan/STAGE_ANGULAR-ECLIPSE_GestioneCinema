import { Component, Input } from '@angular/core';
import { CustomerModel } from '../model/customer';
import { DatePipe } from '@angular/common';

@Component({
  imports: [DatePipe],
  selector: 'app-customer-details',
  styleUrl: './customer-details.css',
  templateUrl: './customer-details.html',
})
export class CustomerDetails {
  @Input({required: true}) customer!: CustomerModel;
}
