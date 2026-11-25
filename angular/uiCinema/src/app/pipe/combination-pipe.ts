import { Pipe, PipeTransform } from "@angular/core";
import { CustomerModel } from "../model/customer";
import { filterByAge, filterByDisability, filterByName, filterBySurname } from "./filter-pipe";



@Pipe({
    name: "combinationPipe",
})
export class CombinationPipe implements PipeTransform {
transform(value: CustomerModel[], name: string | undefined | null, surname: string | undefined | null, disability: boolean | undefined | null, age: number | undefined | null): CustomerModel[] {
        if(name)
            value = filterByName(value, name!);
        if(surname)
            value = filterBySurname(value, surname);
        if(disability && disability! == true)
            value = filterByDisability(value, disability!);
        if(age)
            value = filterByAge(value, age!);
        return value;
    }
}
