import { Pipe, PipeTransform } from "@angular/core";
import { FilmModel } from "../model/film";

@Pipe({
    name: "filmPipe",
})
export class FilmPipe implements PipeTransform{
public transform(array: FilmModel[] | null, value: string | undefined | null): any[] | null{
        
        if(!value)
            return array;
        
        return array!.filter((p) => p.title.toLowerCase().includes(value.toLowerCase()))        
    }
}