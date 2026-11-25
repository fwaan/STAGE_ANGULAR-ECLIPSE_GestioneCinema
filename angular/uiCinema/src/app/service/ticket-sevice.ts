import { Service } from '@angular/core';
import { TicketModel } from '../model/ticket';

@Service()
export class TicketSevice {

    tickets: TicketModel[] = [];

    private readonly baseApi: string = "localhost:8080/ticket";

    constructor(){
        this.fetchTickets();
    }

    private async fetchTickets(){
        const response = await fetch(this.baseApi + "/all");
        if(!response.ok)
            throw new Error(`HTTP error! Status: ${response.status}`);
        this.tickets = await(response.json()) as TicketModel[];
    }
}
