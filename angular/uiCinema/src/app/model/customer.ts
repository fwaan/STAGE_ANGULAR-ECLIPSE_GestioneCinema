import { TicketModel } from "./ticket";

export interface CustomerModel {
    id: number;
    nome: string;
    cognome: string;
    dob: Date;
    disability: boolean;
    //tickets: Array<TicketModel>;
    
}
