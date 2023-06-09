import {Pipe, PipeTransform} from '@angular/core';

@Pipe({
  name: 'formName'
})
export class FormNamePipe implements PipeTransform {
  transform(value: string, formsData: any): string {
    const formName = formsData.filter(a => a.id === value);
    return formName[0].name;
  }
}
