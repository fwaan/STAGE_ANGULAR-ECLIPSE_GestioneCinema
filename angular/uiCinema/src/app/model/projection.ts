import { FilmModel } from "./film";
import { TicketModel } from "./ticket";


export interface ProjectionModel{
    id: number;
    hall: string;
    projection_time: Date;
    //film_id: FilmModel;
    capacity: number;
    //tickets: Array<TicketModel>;

}
