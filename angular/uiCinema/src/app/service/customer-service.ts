import { inject, Service } from '@angular/core';
import { CustomerModel } from '../model/customer';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';

@Service()
export class CustomerService {


    private readonly apiBase: string = "http://localhost:8080/customer";

    fetcher: HttpClient = inject(HttpClient);

    /*
    private async fetchCustomers(){
        const response = await fetch(this.apiBase + "/all");

        if(!response.ok)
            throw new Error(`HTTP error! Status: ${response.status}`);

        this.customers = await (response.json()) as CustomerModel[];
    }*/

    getAll(): Observable<CustomerModel[]>{
        return this.fetcher.get<CustomerModel[]>(this.apiBase + "/all");
    }
}
