import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
    name: 'francescoPipe',
    standalone: true,
})
export class FrancescoPipe implements PipeTransform {
    transform<T extends object>(
        items: T[] | null,
        filtri: Partial<Record<keyof T, string>> | null
    ): T[] {
        const lista = items ?? [];
        if (!filtri) return lista;

        const attivi = Object.entries(filtri as Record<string, string>)
            .filter(([, valore]) => String(valore ?? '').trim() !== '');

        if (attivi.length === 0) return lista;

        return lista.filter(item =>
            attivi.every(([chiave, valore]) =>
                String(item[chiave as keyof T] ?? '')
                    .toLowerCase()
                    .includes(String(valore).toLowerCase().trim())
            )
        );
    }
}