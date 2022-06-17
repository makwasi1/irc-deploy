import {NgModule} from "@angular/core";
import {LongTermApplicationComponent} from "./long-term-application.component";
import {LongTermGrantComponent} from "./long-term-grant.component";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {NgxDatatableModule} from "@swimlane/ngx-datatable";
import {ComponentsModule} from "../../../components/components.module";
import {DataTablesModule} from "angular-datatables";
import {EntityViewTableModule} from "../../entity-views/entity-view-table/entity-view-table.module";
import {NgSelectModule} from "@ng-select/ng-select";
import {CommonModule} from "@angular/common";
import {LongTermGrantRoutingModule} from "./long-term-grant-routing.module";
import {GrantProcessModule} from "../grant-process.module";
import {StartLongTermGrantComponent} from "./start-long-term-grant.component";

@NgModule({
  declarations: [LongTermGrantComponent, LongTermApplicationComponent, StartLongTermGrantComponent],
  imports: [
    CommonModule,
    LongTermGrantRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    NgxDatatableModule,
    ComponentsModule,
    DataTablesModule,
    EntityViewTableModule,
    NgSelectModule,
    GrantProcessModule,
  ]
})
export class LongTermGrantModule { }
