import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { PartnerListComponent } from './partner-list.component';
import { PartnerListRoutingModule } from './partner-list-routing.module';
import {FormsModule} from "@angular/forms";
import {NgxDatatableModule} from "@swimlane/ngx-datatable";
import {ComponentsModule} from "../../../components/components.module";
import {DataTablesModule} from "angular-datatables";
import {EntityViewTableModule} from "../../entity-views/entity-view-table/entity-view-table.module";

@NgModule({
  declarations: [PartnerListComponent],
  imports: [
    CommonModule,
    PartnerListRoutingModule,
    FormsModule,
    NgxDatatableModule,
    ComponentsModule,
    DataTablesModule,
    EntityViewTableModule,
  ]
})
export class PartnerListModule { }
