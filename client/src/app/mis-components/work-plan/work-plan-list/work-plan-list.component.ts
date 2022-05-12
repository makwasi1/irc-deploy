import {Component, OnInit} from '@angular/core';
import {Subject} from "rxjs";
import {Router} from "@angular/router";
import {WorkPlanService} from "../../../services/work-plan-setup.service";
import {AlertService} from "../../../services/alert";

@Component({
  selector: 'app-partner-list',
  templateUrl: './work-plan-list.component.html',
  styleUrls: ['./work-plan-list.component.css']
})
export class WorkPlanListComponent implements OnInit {

  rows: any;
  dtOptions: any = {};
  dtTrigger: Subject<any> = new Subject<any>();

  constructor(private router: Router, private workPlanService: WorkPlanService, private alertService: AlertService) {
  }

  ngOnInit(): void {
    this.workPlanService.getWorkPlan().subscribe(data => {
      this.rows = data;
      this.dtTrigger.next();
    }, error => console.log(error));

    this.dtOptions = {
      pagingType: "numbers",
      lengthMenu: [[10, 25, 50, -1], [10, 25, 50, "All"]],
      processing: true,
      responsive: true,
      dom: 'lfBrtip',
      buttons: [
        {
          text: '<i class="text fas fa-plus" style="color: cornflowerblue;"></i>&nbsp;&nbsp;Add Work Plan',
          action: (e, dt, node, config) => {
            this.router.navigate(['/workPlan']);
          }
        },
        {
          text: '<i class="fas fa-file-csv" style="color: green;"></i>&nbsp;&nbsp;Export to CSV',
          extend: 'csvHtml5',
          title: 'Setup'
        },
        {
          text: '<i class="far fa-file-excel" style="color: green;"></i>&nbsp;&nbsp;Export to Excel',
          extend: 'excelHtml5',
          title: 'TaskList'
        }
      ]
    };
  }

  deleteRecord(id) {
    if (confirm('Are you sure to delete this Partner Setup Record?')) {
      this.workPlanService.deleteWorkPlanRecord(id).subscribe((result) => {
          this.alertService.warning(`Staff Work plan Record has been  deleted `);
          this.reloadTableData();
        }, error => {
          this.alertService.error(`Staff Work plan Record could not be deleted`);
        }
      );
    }
  }

  private reloadTableData() {
    this.workPlanService.getWorkPlan().subscribe(data => {
      this.rows = data;
    }, error => console.log(error));
  }
}
