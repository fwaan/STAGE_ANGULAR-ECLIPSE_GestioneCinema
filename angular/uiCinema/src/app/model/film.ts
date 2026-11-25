import { ProjectionModel } from "./projection";

export interface FilmModel {
    id: number;
    title: string;
    duration_in_minutes: number;
    genre: string;
    starter_price: number;
    //projections: Array<ProjectionModel>;
}
