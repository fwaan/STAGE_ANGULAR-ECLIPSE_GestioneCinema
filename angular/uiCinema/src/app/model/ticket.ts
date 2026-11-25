import { CustomerModel } from "./customer";
import { ProjectionModel } from "./projection";

export interface TicketModel {
    seat: number;
    projection_id: ProjectionModel;
    customer_id: CustomerModel;
    ticket_type: string;
    payment_method: string;
}
