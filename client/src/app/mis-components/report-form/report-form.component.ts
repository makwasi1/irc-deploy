import {Component, OnInit, ViewChild} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {Subject} from "rxjs";
import {ReportFormService} from "../../services/report-form.service";
import {CommentNode} from '../comments/comments.component';
import {Location} from '@angular/common';
import {CellEdit, OnUpdateCell} from '../../utilities/cell_edit';
import {FileUploadService} from '../../services/file-upload.service';
import {v4 as uuid} from 'uuid';
import {AuthService} from "../../services/auth.service";
import {TaskListService} from "../../services/task-list.service";
import {HttpParams} from "@angular/common/http";
import {DummyData} from "../../utilities/dummy-data";

@Component({
  selector: 'app-report-form',
  templateUrl: './report-form.component.html',
  styleUrls: ['./report-form.component.css']
})

export class ReportFormComponent implements OnInit, OnUpdateCell {

  @ViewChild(CellEdit) cellEdit;

  dtOptions: any = {};
  dtTrigger: Subject<any> = new Subject<any>();
  comments: Array<CommentNode> = [];
  recommendations: Array<CommentNode> = [];

  error: boolean;
  success: boolean;
  errorMessage: string;
  successMessage: string;

  isSubmitVisible: boolean;
  isReviewVisible: boolean;
  isApproveVisible: boolean;
  openCommentsPopup: boolean;
  openRecommendationsPopup: boolean;
  openPopup: boolean;
  loading: boolean = false;
  report: any;

  reviewerInformation: any;
  approverInformation: any;

  radioExpensesRealistic: string;
  radioAttachmentsVerified: string;
  radioFiguresRealistic: string;
  radioNarrativeAlign: string;
  radioInlineWithTargets: string;
  radioEvidenceSatisfactory: string;
  reviewerComments: string;
  reviewerRecommendations: string;

  radioSuggestedChangesSatisfactory: string;
  radioReportsWellAligned: string;
  radioRecommendFund: string;
  radioEndOfPartnership: string;
  amountOfFundsDisbursed: string;
  provideAnyRecommendations: string;

  taskId: string;
  taskRecord: any;
  reportValues: [];

  shortLink1: string = "";
  shortLink2: string = "";
  shortLink3: string = "";
  attachment1: string;
  attachment2: string;
  attachment3: string;

  organisationalInfo: any;
  projectInfo: any;
  performanceReport = [];
  financialReport = [];
  items = [
    {name: 'Yes', value: 'yes'},
    {name: 'No', value: 'no'}
  ];

  constructor(private router: Router, private route: ActivatedRoute, private location: Location, private formViewService: ReportFormService, private taskListService: TaskListService, private fileUploadService: FileUploadService, public authService: AuthService) {
  }

  ngOnInit(): void {
    this.isSubmitVisible = false;
    this.isReviewVisible = false;
    this.isApproveVisible = false;

    this.organisationalInfo = DummyData.organisationalInfo;
    this.projectInfo = DummyData.projectInfo;

    this.route.params
      .subscribe(p => {
        this.taskId = p['id'];
        const params = new HttpParams().set('id', this.taskId);
        this.taskListService.getTaskRecord(params).subscribe((data) => {
          this.taskRecord = data;
          if (this.taskRecord.taskDefinitionKey === "Submit_Report") this.isSubmitVisible = true;
          if (this.taskRecord.taskDefinitionKey === "Review_Report") this.isReviewVisible = true;
          if (this.taskRecord.taskDefinitionKey === "Approve_Report") this.isApproveVisible = true;

          const params = new HttpParams()
            .set('taskId', this.taskId);

          this.setAttachments(params);

          this.setCommentsAndRecommendations(params);

          this.setReportsData(params);

        }, error => console.log(error));
      });

    this.dtOptions = {
      pagingType: "numbers",
      lengthMenu: [[5, 10, 25, 50, -1], [5, 10, 25, 50, "All"]],
      processing: true,
      responsive: true,
      dom: 'lfBrtip',
      buttons: []
    };
  }

  setCommentsAndRecommendations(params) {
    this.formViewService.getCommentsForTask(params).subscribe(data => {
      if (data.comments !== null && data.comments !== undefined) {
        data.comments.forEach((c) => {
          this.comments.push(new CommentNode(c.id, c.content, c.userId, [], this.getRepliesToComments(c.children), new Date(c.dateCreated)));
        });
      }
    }, error => console.log("Error getting comments", error));

    this.formViewService.getRecommendationsForTask(params).subscribe(data => {
      if (data.recommendations !== null && data.recommendations !== undefined) {
        data.recommendations.forEach((r) => {
          this.recommendations.push(new CommentNode(r.id, r.content, r.userId, [], [], new Date(r.dateCreated)));
        });
      }
    }, error => console.log("Error getting recommendations", error));
  }

  setReportsData(params) {
    this.formViewService.getReportForTask(params).subscribe(data => {
      if (data.report !== null && data.report !== undefined) {
        this.report = data.report;
        let reports = JSON.parse(data.report.reportValues);
        this.financialReport = JSON.parse(reports.financialReport);
        this.performanceReport = JSON.parse(reports.performanceReport);


        if (reports.reviewerInformation !== null && reports.reviewerInformation !== undefined) {
          this.reviewerInformation = JSON.parse(reports.reviewerInformation);
          this.radioExpensesRealistic = this.reviewerInformation.expenses_realistic;
          this.radioAttachmentsVerified = this.reviewerInformation.attachments_verified;
          this.radioFiguresRealistic = this.reviewerInformation.figures_realistic;
          this.radioNarrativeAlign = this.reviewerInformation.narrative_align;
          this.radioInlineWithTargets = this.reviewerInformation.inline_with_targets;
          this.radioEvidenceSatisfactory = this.reviewerInformation.evidence_satisfactory;
          this.reviewerComments = this.reviewerInformation.comments;
          this.reviewerRecommendations = this.reviewerInformation.recommendations;
        }

        if (reports.approverInformation !== null && reports.approverInformation !== undefined) {
          this.approverInformation = JSON.parse(reports.approverInformation);
          this.radioSuggestedChangesSatisfactory = this.approverInformation.suggested_changes_satisfactory;
          this.radioReportsWellAligned = this.approverInformation.reports_well_aligned;
          this.radioRecommendFund = this.approverInformation.recommend_fund;
          this.radioEndOfPartnership = this.approverInformation.end_of_partnership;
          this.amountOfFundsDisbursed = this.approverInformation.amountOfFundsDisbursed;
          this.provideAnyRecommendations = this.approverInformation.provideAnyRecommendations;
        }
      } else {
        this.financialReport = DummyData.financialReport;
        this.performanceReport = DummyData.performanceReport;
      }

      this.dtTrigger.next();
    }, error => console.log("Error getting reports", error));
  }

  setAttachments(params) {
    this.formViewService.getAttachmentsForTask(params).subscribe(data => {
      if (data.files !== null) {
        data.files.forEach((file) => {
          if (file.name === "attachment1") {
            this.attachment1 = file.path;
            this.shortLink1 = file.path;
          }
          if (file.name === "attachment2") {
            this.attachment2 = file.path;
            this.shortLink2 = file.path;
          }
          if (file.name === "attachment3") {
            this.attachment3 = file.path;
            this.shortLink3 = file.path;
          }
        });
      }
    }, error => console.log("Error getting attachments", error));
  }

  getRepliesToComments(list) {
    let answers = [];
    if (list !== null && list !== undefined && !Array.isArray(list)) {
      JSON.parse(list).forEach((answer) => {
        answers.push(new CommentNode(answer.id, answer.text, answer.user, answer.likes, this.getRepliesToComments(answer.answers), new Date(answer.dateTimeCreated)));
      });
    }
    return answers;
  }


  commentsChangedHandler(comments: Array<CommentNode>) {
    this.comments = comments;
    console.log(comments);
  }

  recommendationsChangedHandler(recommendations: Array<CommentNode>) {
    this.recommendations = recommendations;
    console.log(recommendations);
  }

  handleFileInput(event) {
    let files: FileList = event.target.files;
    if (event.target.id === "attachment1") this.attachment1 = files.item(0).name;
    if (event.target.id === "attachment2") this.attachment2 = files.item(0).name;
    if (event.target.id === "attachment3") this.attachment3 = files.item(0).name;
    this.uploadFile(files.item(0), event.target.id);
  }

  uploadFile(file, id) {
    this.loading = !this.loading;
    console.log(file);
    this.fileUploadService.upload(file).subscribe(
      (event: any) => {
        if (typeof (event) === 'object') {

          // Short link via api response
          if (id === "attachment1") this.shortLink1 = event.link;
          if (id === "attachment2") this.shortLink2 = event.link;
          if (id === "attachment3") this.shortLink3 = event.link;

          console.log("shortlink", this.shortLink1);

          this.loading = false; // Flag variable
        }
      }
    );
  }

  changeForm(formName) {
    this.isSubmitVisible = false;
    this.isReviewVisible = false;
    this.isApproveVisible = false;
    if (formName == 'Submit') this.isSubmitVisible = true;
    if (formName == 'Review') this.isReviewVisible = true;
    if (formName == 'Approve') this.isApproveVisible = true;
    window.scroll(0, 0);
    this.success = false;
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

  viewRecommendations(): void {
    this.openRecommendationsPopup = !this.openRecommendationsPopup;
    this.openPopup = this.openRecommendationsPopup;
  }

  addRecommendation() {
    let text = (document.getElementById("addRecommendation") as HTMLTextAreaElement);
    if (text.value !== "") {
      this.recommendations.push(new CommentNode(uuid(), text.value, this.authService.getLoggedInUsername(), [], [], new Date()));
      text.value = "";
    }
  }

  get performance() {
    return this.performanceReport;
  }

  get financial() {
    return this.financialReport;
  }

  saveCellValue = (value: string, key: string, rowId): void => {
    //save
    console.log("newValue", value);
    if (value !== null && value !== undefined)
      switch (key) {
        case "summaryComment":
          if (this.performanceReport.some(x => x.id === rowId)) {
            this.performanceReport.forEach(function (comment) {
              if (comment.id === rowId) comment.comment_on_result = value
            });
          }
          break;
        case "quarterExpense":
          if (this.financialReport.some(x => x.id === rowId)) {
            this.financialReport.forEach(function (item) {
              if (item.id === rowId) item.quarter_expenses = value
            });
          }
          break;
        case "variance":
          if (this.financialReport.some(x => x.id === rowId)) {
            this.financialReport.forEach(function (item) {
              if (item.id === rowId) item.variance = value
            });
          }
          break;
        case "reason_for_variance":
          if (this.financialReport.some(x => x.id === rowId)) {
            this.financialReport.forEach(function (item) {
              if (item.id === rowId) item.reason_for_variance = value
            });
          }
          break;
      }
  }

  cellEditor(row, td_id, key: string, oldValue) {
    new CellEdit().edit(row.id, td_id, '', oldValue, key, this.saveCellValue);
  }

  saveReport(reportValues: { [key: string]: string }, status) {
    let reportRecord: { [key: string]: string } = {
      taskId: this.taskRecord.id,
      processId: this.taskRecord.processInstanceId,
      userId: this.authService.getLoggedInUsername(),
      groupId: this.taskRecord.groupId,
      taskDefinitionKey: this.taskRecord.taskDefinitionKey,
      reportValues: JSON.stringify(reportValues),
      status: status
    }
    if (this.report) {
      this.formViewService.updateReport(reportRecord, this.report.id).subscribe((data) => {
        this.error = false;
        this.success = true; this.successMessage = "Updated Report";
      }, error => {
        this.error = true; this.errorMessage = "Failed to update Report";
        this.success = false;
        console.log(error);
      });
    } else {
      this.formViewService.createReport(reportRecord).subscribe((data) => {
        this.error = false;
        this.success = true; this.successMessage = "Saved Report";
      }, error => {
        this.error = true; this.errorMessage = "Failed to save Report";
        this.success = false;
        console.log(error);
      });
    }

    setTimeout(()=>{
      this.success = false;
      this.error = false;
    }, 3000);
  }

  submitReport(status) {
    this.error = false;
    this.success = false;

    let reportValues: { [key: string]: string } = {
      financialReport: JSON.stringify(this.financialReport),
      performanceReport: JSON.stringify(this.performanceReport),
      reviewerInformation: JSON.stringify(this.reviewerInformation),
      approverInformation: JSON.stringify(this.approverInformation)
    }

    let attachments = [];
    attachments.push({key: 'attachment1', value: this.attachment1});
    attachments.push({key: 'attachment2', value: this.attachment2});
    attachments.push({key: 'attachment3', value: this.attachment3});

    attachments.forEach((attachment) => {
      if (attachment != null) {
        let fileRecord: { [key: string]: string } = {
          taskId: this.taskRecord.id,
          processId: this.taskRecord.processInstanceId,
          userId: this.authService.getLoggedInUsername(),
          groupId: this.taskRecord.groupId,
          taskDefinitionKey: this.taskRecord.taskDefinitionKey,
          path: attachment.value,
          name: attachment.key,
        }

        const params = new HttpParams().set('taskId', this.taskId).set('name', attachment.key);
        this.formViewService.getFileByTaskAndName(params).subscribe((data) => {
          let record = data.fileRecord;
          if (record == null) {
            this.formViewService.saveFile(fileRecord).subscribe((data) => {
              console.log('saved file successfully')
            }, error => console.log('file', error));
          } else {
            console.log('record', record);
            this.formViewService.updateFile(fileRecord, record.id).subscribe((data) => {
              console.log('updated file successfully')
            }, error => console.log('file', error));
          }
          console.log('file', record);
        }, error => console.log(error));

      }
    });

    if (status === "save") {
      this.saveReport(reportValues, 'saved_for_later');
      this.updateTaskStatus("In_Progress");
      this.location.back();
    }
    if (status === "submit") {
      this.saveReport(reportValues, 'final_submission');
      this.updateTaskStatus("Under_Review");
      this.changeForm('Review');
    }
  }

  reviewReport(status) {
    this.error = false;
    this.success = false;

    let reportValues: { [key: string]: string } = {
      financialReport: JSON.stringify(this.financialReport),
      performanceReport: JSON.stringify(this.performanceReport),
      approverInformation: JSON.stringify(this.approverInformation),
      reviewerInformation: JSON.stringify({
        expenses_realistic: this.radioExpensesRealistic,
        attachments_verified: this.radioAttachmentsVerified,
        figures_realistic: this.radioFiguresRealistic,
        narrative_align: this.radioNarrativeAlign,
        inline_with_targets: this.radioInlineWithTargets,
        evidence_satisfactory: this.radioEvidenceSatisfactory,
        comments: this.reviewerComments,
        recommendations: this.reviewerRecommendations
      })
    }

    if (status === "revise") {
      this.saveReport(reportValues, 'asked_for_revisions');
      this.updateTaskStatus("Needs_Revision");
      this.location.back();
    }
    if (status === "submit") {
      this.saveReport(reportValues, 'reviewed_and_submitted');
      this.updateTaskStatus("Pending_Approval");
      this.changeForm('Approve');
    }
  }

  approveReport() {
    this.comments.forEach((comment) => {
      let commentsRecord: { [key: string]: string } = {
        taskId: this.taskRecord.id,
        processId: this.taskRecord.processInstanceId,
        userId: comment.user,
        groupId: this.taskRecord.groupId,
        taskDefinitionKey: this.taskRecord.taskDefinitionKey,
        content: comment.text,
        children: JSON.stringify(comment.answers),
      }
      const params = new HttpParams().set('id', comment.id);
      this.formViewService.getComment(params).subscribe((data) => {
        if (data.comment === null || data.comment === undefined) {
          this.formViewService.saveComment(commentsRecord).subscribe((data) => {
            console.log('saved comment successfully')
          }, error => console.log('save comment', error));
        }
      }, error => console.log('comment', error));
    });

    this.recommendations.forEach((recommendation) => {
      let recommendationsRecord: { [key: string]: string } = {
        taskId: this.taskRecord.id,
        processId: this.taskRecord.processInstanceId,
        userId: recommendation.user,
        groupId: this.taskRecord.groupId,
        taskDefinitionKey: this.taskRecord.taskDefinitionKey,
        content: recommendation.text,
      }
      const params = new HttpParams().set('id', recommendation.id);
      this.formViewService.getRecommendation(params).subscribe((data) => {
        if (data.recommendation === null || data.recommendation === undefined) {
          this.formViewService.saveRecommendation(recommendationsRecord).subscribe((data) => {
            console.log('saved recommendation successfully')
          }, error => console.log('save recommendation', error));
        }
      }, error => console.log('recommendation', error));
    });

    let reportValues: { [key: string]: string } = {
      financialReport: JSON.stringify(this.financialReport),
      performanceReport: JSON.stringify(this.performanceReport),
      reviewerInformation: JSON.stringify(this.reviewerInformation),
      approverInformation: JSON.stringify({
        suggested_changes_satisfactory: this.radioSuggestedChangesSatisfactory,
        reports_well_aligned: this.radioReportsWellAligned,
        recommend_fund: this.radioRecommendFund,
        end_of_partnership: this.radioEndOfPartnership,
        amountOfFundsDisbursed: this.amountOfFundsDisbursed,
        provideAnyRecommendations: this.provideAnyRecommendations
      })
    }

    this.saveReport(reportValues, 'approved_report');
    this.updateTaskStatus("Approved");
    this.location.back();
  }

  updateTaskStatus(status) {
    this.taskRecord.status = status;
    this.taskListService.updateTask(this.taskRecord, this.taskRecord.id).subscribe((data) => {
      console.log('successfully updated task');
    }, error => console.log('update task', error));
  }

  onBackPressed() {
    this.location.back();
  }
}