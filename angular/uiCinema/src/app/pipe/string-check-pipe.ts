import { Pipe, PipeTransform } from "@angular/core";

@Pipe({
    name: "stringFilter"
})
export class StringCheckPipe implements PipeTransform{
    transform(value: string, key: string): boolean {
        return value == key;
    }
}
