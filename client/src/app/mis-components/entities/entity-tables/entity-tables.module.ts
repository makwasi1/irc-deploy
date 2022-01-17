import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { EntityTablesRoutingModule } from './entity-tables-routing.module';
import {EntityTablesComponent} from "./entity-tables.component";
import {NgxDatatableModule} from "@swimlane/ngx-datatable";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {NgSelectModule} from "@ng-select/ng-select";
import {ReplacePipe} from "../../../replace-pipe";


@NgModule({
  declarations: [EntityTablesComponent, ReplacePipe],
  imports: [
    CommonModule,
    EntityTablesRoutingModule,
    NgxDatatableModule,
    ReactiveFormsModule,
    FormsModule,
    NgSelectModule
  ]
})
export class EntityTablesModule { }
