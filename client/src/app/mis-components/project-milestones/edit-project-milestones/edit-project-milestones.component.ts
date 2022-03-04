import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {ActivatedRoute, Router} from "@angular/router";
import {AlertService} from "../../../services/alert";
import {ProjectMilestoneService} from "../../../services/project-milestone.service";
import {HttpParams} from "@angular/common/http";

@Component({
  selector: 'app-edit-project-milestones',
  templateUrl: './edit-project-milestones.component.html',
  styleUrls: ['./edit-project-milestones.component.css']
})
export class EditProjectMilestonesComponent implements OnInit {

  formGroup: FormGroup;
  submitted = false;
  formData: any;
  milestoneId: any;
  programs = [];
  categories = [];
  constructor(private formBuilder: FormBuilder,
              private route: ActivatedRoute,
              private alertService: AlertService,
              private router: Router,
              private projectMilestoneService: ProjectMilestoneService) { }

  ngOnInit(): void {
    this.milestoneId = this.route.snapshot.params.id
    this.projectMilestoneService.getCurrentMilestone(this.milestoneId).subscribe((results: any) => {
      this.formGroup = this.formBuilder.group({
        program: [results?.programId, [Validators.required]],
        category: [results?.category, [Validators.required]],
        name: [results?.name, [Validators.required]],
        description: [results?.description],
        reportingQuery: [results?.reportingQuery],
        dashboardQuery: [results?.dashboardQuery]
      });
    });
    this.projectMilestoneService.getPrograms().subscribe(data => {
      this.programs = data;
    }, error => console.log(error));
  }

  getCategories(value) {
    const params = new HttpParams()
      .set('id', value);
    this.projectMilestoneService.getProgramCategories(params).subscribe(data => {
      this.categories = data;
    }, error => console.log(error));
  }

  get f() {
    return this.formGroup.controls;
  }

  editMilestone() {
    this.submitted = true;
    if (this.formGroup.invalid) {
      console.log('Invalid');
      return;
    }
    this.formData = this.formGroup.value;
    let reportingTable = {"reportingTable": 'milestone_reporting_table'};
    let dashboardTable = {"dashboardTable": 'milestone_dashboard_table'};
    this.formData = Object.assign(this.formData, reportingTable);
    this.formData = Object.assign(this.formData, dashboardTable);
    this.projectMilestoneService.updateMilestone(this.milestoneId, this.formData).subscribe(results => {
      this.router.navigate(['/milestones']);
      this.alertService.success(`Milestone has been successfully updated `);
    }, error => {
      this.alertService.error(`Milestone could not be updated`);
    });

    if (this.formGroup.valid) {
      setTimeout(() => {
        this.formGroup.reset();
        this.submitted = false;
      }, 100);
    }
  }

  cancel(): void {
    window.history.back();
  }

}
