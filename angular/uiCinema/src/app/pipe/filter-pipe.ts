import { Pipe, PipeTransform } from "@angular/core";
import { CustomerModel } from "../model/customer";



export function filterByName(value: CustomerModel[], key: string): CustomerModel[]{
    return value.filter((c) => c.nome == key);
}

export function filterBySurname(value: CustomerModel[], key: string): CustomerModel[]{
    return value.filter((c) => c.cognome == key);
}

export function filterByDisability(value: CustomerModel[], key: boolean | null): CustomerModel[]{
    return value.filter((c) => c.disability);
}

export function filterByAge(value: CustomerModel[], key: number): CustomerModel[]{
    return value.filter((c) =>{
        new Date(Date.now()).getFullYear() - c.dob.getFullYear() >= key
    })
}

@Pipe({
    name: "filterPipe",
    pure: false,
})

//L'ho rotta
export class FilterPipe implements PipeTransform {
    public transform(array: any[] | null, param: string, value: string | undefined | null): any[] | null{
        
        if(!value)
            return array;
        
        return array!.filter((p) => String(p[param].toLowerCaser().includes(value)))        
    }
}