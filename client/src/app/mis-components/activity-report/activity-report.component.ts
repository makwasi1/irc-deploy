import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {AlertService} from "../../services/alert";
import {ProgramStaffService} from "../../services/program-staff.service";
import {ActivityReportService} from "../../services/activity-report.service";

@Component({
  selector: 'app-activity-report',
  templateUrl: './activity-report.component.html',
  styleUrls: ['./activity-report.component.css']
})
export class ActivityReportComponent implements OnInit {
  entries: number = 10;
  selected: any[] = [];
  groupId = '';
  search = '';
  private searchValue = '';
  submitted = false;
  activeRow: any;
  staffs: any;
  activity: any;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private alertService: AlertService,
    private activityReportService: ActivityReportService,
    private programStaffService : ProgramStaffService
  ) { }

  ngOnInit(): void {
    this.reloadTable();
    this.getStaff();
  }

  reloadTable() {
    this.activityReportService.getActivityReport().subscribe((data) => {
      console.log("JHerer",data)
      this.activity = data;
    });
  }

  getStaff(){
    this.programStaffService.getProgramStaffs().subscribe((data) => {
      console.log()
      this.staffs = data;
    });
  }

  createActivityReport() {
    this.router.navigate(['activity-create']);
  }

  editActivityReport(row) {
    const id = row.id;
    this.router.navigate(['/activityReport/edit/'+ id]);
  }

  deleteActivityReport(row) {
    const deletedRow = row.id;
    if (confirm('Are you sure to delete this Activity Report?')) {
      this.activityReportService.deleteCurrentActivityReport(deletedRow).subscribe((result) => {
          this.alertService.warning(`Activity Report has been  deleted `);
          this.router.navigate(['activity-list']);
          this.reloadTable();
        }, error => {
          this.alertService.error(`Activity Report could not be deleted`);
        }
      );
    }
  }

  entriesChange($event) {
    this.entries = $event.target.value;
    this.reloadTable();
  }

  filterTable($event) {
    this.search = $event.target.value;
    this.reloadTable();
  }

  onSelect({selected}) {
    this.selected.splice(0, this.selected.length);
    this.selected.push(...selected);
  }

  onActivate(event) {
    this.activeRow = event.row;
  }

  onChangeSearch(event) {
    if (!event.target.value)
      this.searchValue = ''
    else {
      this.searchValue = event.target.value;
    }
    this.reloadTable();
  }
}
