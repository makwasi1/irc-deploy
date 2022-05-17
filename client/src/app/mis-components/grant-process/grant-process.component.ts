import {Component, OnInit} from '@angular/core';
import {CommentNode} from "../comments/comments.component";
import {AuthService} from "../../services/auth.service";
import {v4 as uuid} from 'uuid';
import {GrantProcessService} from "../../services/grant-process.service";
import {ActivatedRoute, Router} from "@angular/router";
import {TaskListService} from "../../services/task-list.service";
import {HttpParams} from "@angular/common/http";
import {FileUploadService} from "../../services/file-upload.service";
import {AlertService} from "../../services/alert";

@Component({
  selector: 'app-grant-process',
  templateUrl: './grant-process.component.html',
  styleUrls: ['./grant-process.component.css']
})
export class GrantProcessComponent implements OnInit {
  isSubmitLetterOfInterest: boolean;
  isReviewLetterOfInterest: boolean;
  isPlanningLearningApplication: boolean;
  isReviewPlanningLearningGrant: boolean;
  isApprovePlanningLearningGrant: boolean;
  isProvidePlanningLearningGrant: boolean;
  isSubmitReport: boolean;
  isReviewReport: boolean;

  taskRecord: any;

  loading: boolean;
  isReadOnly: boolean;
  error: boolean;
  success: boolean;
  errorMessage: string;
  successMessage: string;

  hasApplicationBeenReviewed: any;
  dateOfDueDiligence: any;
  attachmentDiligenceReport: any;
  reviewerComments: any;

  items = [
    {name: 'Yes', value: 'Yes'},
    {name: 'No', value: 'No'}
  ];
  decision = [
    {name: 'Proceed with application', value: 'Yes'},
    {name: 'Unsuccessful', value: 'No'}
  ];
  decision2 = [
    {name: 'Proceed with application', value: 'Yes'},
    {name: 'Return to Program Officer for further Review', value: 'No'}
  ];
  isConceptInline: any;
  doesItAdhere: any;
  areTheyAdhering: any;
  decisionOfReviewProcess: any;
  hasDueDiligenceConducted: any;
  grantId: string;
  definitionKey: string;
  processInstanceId: string;
  decisionOfApproveProcess: any;
  approveComments: any;

  comments: Array<CommentNode> = [];
  openCommentsPopup: boolean;
  openPopup: boolean;
  financeSectionComments: any;
  leadAgency: any;
  grantAmount: any;
  periodFrom: any;
  periodTo: any;
  clusterName: any;

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    public authService: AuthService,
    private grantProcessService: GrantProcessService,
    private taskListService: TaskListService,
    public fileUploadService: FileUploadService,
    private alertService: AlertService
  ) {
  }

  ngOnInit(): void {
    this.isSubmitLetterOfInterest = false;
    this.isReviewLetterOfInterest = false;
    this.isPlanningLearningApplication = false
    this.isReviewPlanningLearningGrant = false;
    this.isApprovePlanningLearningGrant = false;
    this.isProvidePlanningLearningGrant = false;
    this.isSubmitReport = false;
    this.isReviewReport = false;

    this.route.params
      .subscribe(p => {
        this.isReadOnly = p['readonly'] == 'true';

        if (p['id'] != undefined) {
          const params = new HttpParams().set('id', p['id']);
          this.taskListService.getTaskRecord(params).subscribe((data) => {
            this.taskRecord = data
            if (data.taskDefinitionKey === "Review_and_Conduct_Due_Diligence") {
              this.isReviewLetterOfInterest = true;
              this.grantProcessService.getReviewRecord(data.grantId).subscribe((data) => {
                console.log('review record available', data)
              })
            }
            if (data.taskDefinitionKey === "Activity_142yglq") {
              this.isPlanningLearningApplication = true;
              this.grantProcessService.getPlanningAndLearningRecord(data.grantId).subscribe((data) => {
                console.log('apply PandG record available', data)
              })
            }
            if (data.taskDefinitionKey === "Review_Concept") {
              this.isReviewPlanningLearningGrant = true;
              this.grantProcessService.getPlanningAndLearningReview(data.grantId).subscribe((data) => {
                console.log('apply PlanningAndLearningReview record available', data)
              })
            }
            if (data.taskDefinitionKey === "Approve_Learning_Grant") {
              this.isApprovePlanningLearningGrant = true;
              this.grantProcessService.getPlanningAndLearningApprove(data.grantId).subscribe((data) => {
                console.log('apply PlanningAndLearningApprove record available', data)
              })
            }
            if (data.taskDefinitionKey === "Provide_Learning_Grant") {
              this.isProvidePlanningLearningGrant = true;
              this.grantProcessService.getProvideLearningGrant(data.grantId).subscribe((data) => {
                console.log('apply ProvideLearningGrant record available', data)
              })
            }
            if (data.taskDefinitionKey === "Submit_Report") {
              this.isSubmitReport = true;
            }
            if (data.taskDefinitionKey === "Review_Report") {
              this.isReviewReport = true;
            }
            this.grantId = data.grantId;
            this.definitionKey = data.taskDefinitionKey
            this.processInstanceId = data.processInstanceId
          })
        } else {
          this.isSubmitLetterOfInterest = true
        }
      });
  }


  handleFileInput(event) {
    let files: FileList = event.target.files;
    this.uploadFile(files.item(0), event.target.id);
  }

  uploadFile(file, id) {
    this.loading = !this.loading;
    this.fileUploadService.upload(file, 'PandL_Grant').subscribe((data) => {
      if (id === "attachmentDiligenceReport") this.attachmentDiligenceReport = data.path;
      this.loading = false;
    }, error => {
      console.log(error)
    });
  }

  viewComments(): void {
    this.openCommentsPopup = !this.openCommentsPopup;
    this.openPopup = this.openCommentsPopup;
  }

  addComment() {
    let text = (document.getElementById("addComment") as HTMLTextAreaElement);
    if (text.value !== "") {
      this.comments.push(new CommentNode(uuid(), text.value, this.authService.getLoggedInUsername(), [], [], new Date()));
      text.value = "";
    }
  }

  commentsChangedHandler(comments: Array<CommentNode>) {
    this.comments = comments;
    console.log(comments);
  }

  statusChangedHandler(status: string) {
    console.log('status', status);
    this.taskRecord.status = status
    this.taskListService.updateTask(this.taskRecord, this.taskRecord.id).subscribe((data) => {
      console.log('successfully updated task');
    }, error => console.log('update task', error));
  }

  submit(key, status) {
    switch (key) {
      case 'reviewLetter':
        this.submitReviewLetterOfInterest(status)
        break
      case 'reviewConcept':
        this.planningAndLearningReview(status)
        break
      case 'approveConcept':
        this.planningAndLearningApprove(status)
        break
      case 'provideLearningGrant':
        this.provideLearningGrant(status)
        break
    }

    setTimeout(() => {
      if (status != "draft" && this.success) this.router.navigate(['/taskList']);
      this.success = false;
      this.error = false;
    }, 3000);
  }

  submitReviewLetterOfInterest(status) {
    if (this.decisionOfReviewProcess != undefined) {
      let formData: { [key: string]: string } = {
        grantId: this.grantId,
        definitionKey: this.definitionKey,
        processInstanceId: this.processInstanceId,
        hasBeenReviewed: this.hasApplicationBeenReviewed,
        dueDiligence: this.hasDueDiligenceConducted,
        dateOfDueDiligence: this.dateOfDueDiligence,
        dueDiligenceReport: this.attachmentDiligenceReport,
        comments: this.reviewerComments,
        decision: this.decisionOfReviewProcess,
        status: status
      }
      this.grantProcessService.createReviewLetterOfInterest(formData).subscribe((data) => {
        console.log('response', data)
        this.error = false;
        this.success = true;
        this.successMessage = "Updated";
        this.taskRecord.outputVariables = '{"reviewSuccessful": "' + this.decisionOfReviewProcess + '"}'
        this.statusChangedHandler(status)
        this.alertService.success(this.successMessage);
      }, error => {
        this.error = true;
        this.errorMessage = "Failed to update";
        this.alertService.error(this.errorMessage);
        this.success = false;
        console.log(error);
      });
    } else {
      this.alertService.error('Please fill in all required details');
      return;
    }
  }

  planningAndLearningReview(status) {
    if (this.decisionOfReviewProcess != undefined) {
      let formData: { [key: string]: string } = {
        grantId: this.grantId,
        definitionKey: this.definitionKey,
        processInstanceId: this.processInstanceId,
        isConceptInline: this.isConceptInline,
        doesItAdhere: this.doesItAdhere,
        areTheyAdhering: this.areTheyAdhering,
        comments: this.reviewerComments,
        decision: this.decisionOfReviewProcess,
        status: status
      }
      this.grantProcessService.createPlanningAndLearningReview(formData).subscribe((data) => {
        console.log('response', data)
        this.error = false;
        this.success = true;
        this.successMessage = "Updated";
        this.taskRecord.outputVariables = '{"reviewConcept": "' + this.decisionOfReviewProcess + '"}'
        this.statusChangedHandler(status)
        this.alertService.success(this.successMessage);
      }, error => {
        this.error = true;
        this.errorMessage = "Failed to update";
        this.alertService.error(this.errorMessage);
        this.success = false;
        console.log(error);
      });
    } else {
      this.alertService.error('Please fill in all required details');
      return;
    }
  }

  planningAndLearningApprove(status) {
    if (this.decisionOfApproveProcess != undefined) {
      let formData: { [key: string]: string } = {
        grantId: this.grantId,
        definitionKey: this.definitionKey,
        processInstanceId: this.processInstanceId,
        comments: this.approveComments,
        decision: this.decisionOfApproveProcess,
        status: status
      }
      this.grantProcessService.createPlanningAndLearningApprove(formData).subscribe((data) => {
        console.log('response', data)
        this.error = false;
        this.success = true;
        this.successMessage = "Updated";
        this.taskRecord.outputVariables = '{"approveGrant": "' + this.decisionOfApproveProcess + '"}'
        this.statusChangedHandler(status)
        this.alertService.success(this.successMessage);
      }, error => {
        this.error = true;
        this.errorMessage = "Failed to update";
        this.alertService.error(this.errorMessage);
        this.success = false;
        console.log(error);
      });
    } else {
      this.alertService.error('Please fill in all required details');
      return;
    }
  }

  provideLearningGrant(status) {
    let formData: { [key: string]: string } = {
      grantId: this.grantId,
      definitionKey: this.definitionKey,
      processInstanceId: this.processInstanceId,
      clusterName: this.clusterName,
      dateFrom: this.periodFrom,
      dateTo: this.periodTo,
      leadAgency: this.leadAgency,
      grantAmount: this.grantAmount,
      comments: this.financeSectionComments,
      status: status
    }
    this.grantProcessService.createProvideLearningGrant(formData).subscribe((data) => {
      console.log('response', data)
      this.error = false;
      this.success = true;
      this.successMessage = "Updated";
      this.statusChangedHandler(status)
      this.alertService.success(this.successMessage);
    }, error => {
      this.error = true;
      this.errorMessage = "Failed to update";
      this.alertService.error(this.errorMessage);
      this.success = false;
      console.log(error);
    });
  }

  cancel() {
    this.router.navigate(['/taskList']);
  }

}
