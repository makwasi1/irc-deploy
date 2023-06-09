import {Component, OnInit, ViewChild} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {Subject} from 'rxjs';
import {ReportFormService} from '../../services/report-form.service';
import {CommentNode} from '../comments/comments.component';
import {Location} from '@angular/common';
import {CellEdit, OnUpdateCell} from '../../helpers/cell-edit';
import {FileUploadService} from '../../services/file-upload.service';
import {v4 as uuid} from 'uuid';
import {AuthService} from '../../services/auth.service';
import {TaskListService} from '../../services/task-list.service';
import {HttpParams} from '@angular/common/http';
import {ProgramPartnersService} from '../../services/program-partners.service';
import {PartnerSetupService} from '../../services/partner-setup.service';
import {ProjectMilestoneService} from '../../services/project-milestone.service';
import {AlertService} from '../../services/alert';
import {Validator} from "../../helpers/validator";

@Component({
  selector: 'app-report-form',
  templateUrl: './report-form.component.html',
  styleUrls: ['./report-form.component.css']
})

export class ReportFormComponent implements OnInit, OnUpdateCell {

  dtOptions: any = {};
  dtTrigger: Subject<any> = new Subject<any>();
  comments: Array<CommentNode> = [];
  recommendations: Array<CommentNode> = [];

  error: boolean;
  success: boolean;
  errorMessage: string;
  successMessage: string;

  isReadOnly: boolean;

  isSubmitVisible: boolean;
  isSubmitFinalVisible: boolean;
  isReviewVisible: boolean;
  isReviewPerformance: boolean;
  isReviewFinance: boolean;
  isReviewProgram: boolean;
  isApproveVisible: boolean;
  isDisburseFunds: boolean;
  isApproveFundDisbursement: boolean;

  totalGrantAmount: number;
  totalAmountSpent: number;
  totalAdvanced: number;
  balance: number;

  openCommentsPopup: boolean;
  openRecommendationsPopup: boolean;
  openPopup: boolean;
  loading: boolean = false;
  submitting: boolean = false;
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
  radioHowToProceed: string;
  amountOfFundsDisbursed: string;
  dateDisbursed: string;
  amountOfFundsRemaining: string;
  provideAnyRecommendations: string;

  recordId: string;
  taskRecord: any;
  reportValues: [];

  shortLink1: string = '';
  shortLink2: string = '';
  shortLink3: string = '';
  attachment1: string;
  attachment2: string;
  attachment3: string;

  organisationalInfo: any;
  organisationsInvolved: any;
  performanceReport = [];
  financialReport = [];
  proceed = [
    {name: 'Approve disbursement of funds', value: 'Yes'},
    {name: 'Reject Fund Disbursement request', value: 'No'}
  ];
  items = [
    {name: 'Yes', value: 'Yes'},
    {name: 'No', value: 'No'}
  ];

  constructor(private router: Router,
              private route: ActivatedRoute,
              private location: Location,
              private reportFormService: ReportFormService,
              private taskListService: TaskListService,
              public fileUploadService: FileUploadService,
              private partnerSetupService: PartnerSetupService,
              private programPartnersService: ProgramPartnersService,
              private projectMilestoneService: ProjectMilestoneService,
              private alertService: AlertService,
              public authService: AuthService) {
  }

  ngOnInit(): void {
    this.isSubmitVisible = false;
    this.isReviewVisible = false;
    this.isReviewProgram = false;
    this.isReviewFinance = false;
    this.isReviewPerformance = false;
    this.isApproveVisible = false;
    this.isSubmitFinalVisible = false;
    this.isDisburseFunds = false;
    this.isApproveFundDisbursement = false;

    this.route.params
      .subscribe(p => {
        this.recordId = p['id'];
        this.isReadOnly = p['readonly'] == 'true';
        const params = new HttpParams().set('id', this.recordId);

        this.taskListService.getTaskRecord(params).subscribe((data) => {
          this.setData(data);
        }, error => {
          this.taskListService.getArchivedRecord(params).subscribe((data) => {
            this.setData(data);
          }, error => console.log(error));
        });
      });

    this.dtOptions = {
      pagingType: 'numbers',
      lengthMenu: [[5, 10, 25, 50, -1], [5, 10, 25, 50, 'All']],
      processing: true,
      responsive: true,
      dom: 'lfBrtip',
      buttons: []
    };
  }

  setData(data) {
    this.taskRecord = data;
    if (this.taskRecord.taskDefinitionKey === 'Submit_Report') {
      this.isSubmitVisible = true;
    }
    if (this.taskRecord.taskDefinitionKey === 'Submit_Final_Report') {
      this.isSubmitFinalVisible = true;
    }
    if (this.taskRecord.taskDefinitionKey === 'Review_Finance_Report' ||
      this.taskRecord.taskDefinitionKey === 'Review_Performance_Report' ||
      this.taskRecord.taskDefinitionKey === 'Review_Program_Report') {
      this.isReviewVisible = true;
    }
    if (this.taskRecord.taskDefinitionKey === 'Review_Finance_Report') {
      this.isReviewFinance = true;
    }
    if (this.taskRecord.taskDefinitionKey === 'Review_Performance_Report') {
      this.isReviewPerformance = true;
    }
    if (this.taskRecord.taskDefinitionKey === 'Review_Program_Report') {
      this.isReviewProgram = true;
    }
    if (this.taskRecord.taskDefinitionKey === 'Disburse_Funds') {
      this.isDisburseFunds = true;
    }
    if (this.taskRecord.taskDefinitionKey === 'Approve_Report') {
      this.isApproveVisible = true;
      this.isReadOnly = false;
    }
    if (this.taskRecord.taskDefinitionKey === 'Approve_Fund_Disbursement') {
      this.isApproveFundDisbursement = true;
    }

    const params = new HttpParams()
      .set('processInstanceId', this.taskRecord.processInstanceId);

    //set organizational Info
    this.programPartnersService.getCurrentProgramPartner(this.taskRecord.partnerId).subscribe((results: any) => {
      if (results !== null) {
        if (results.organisationsInvolved !== undefined)
          if (results.organisationsInvolved.length > 0) {
            this.organisationsInvolved = JSON.parse(results.organisationsInvolved)
          }
        this.organisationalInfo = results;
      }
    });

    this.setAttachments(params);

    this.setCommentsAndRecommendations(params);

    this.setReportsData(params);

  }

  setCommentsAndRecommendations(params) {
    this.reportFormService.getCommentsForTask(params).subscribe(data => {
      if (data.comments !== null && data.comments !== undefined) {
        data.comments.forEach((c) => {
          this.comments.push(new CommentNode(c.id, c.content, c.userId, [], this.getRepliesToComments(c.children), new Date(c.dateCreated)));
        });
      }
    }, error => console.log('Error getting comments', error));

    this.reportFormService.getRecommendationsForTask(params).subscribe(data => {
      if (data.recommendations !== null && data.recommendations !== undefined) {
        data.recommendations.forEach((r) => {
          this.recommendations.push(new CommentNode(r.id, r.content, r.userId, [], [], new Date(r.dateCreated)));
        });
      }
    }, error => console.log('Error getting recommendations', error));
  }

  setReportsData(params) {
    this.reportFormService.getReportForTask(params).subscribe(data => {
      if (data.report !== null && data.report !== undefined) {
        this.report = data.report;
        let reports = JSON.parse(data.report.reportValues);

        this.reportFormService.getFinancialReportByReportId(data.report.id).subscribe(f => {
          if (f !== undefined) this.financialReport = f;
        });

        this.reportFormService.getPerformanceReportByReportId(data.report.id).subscribe(p => {
          if (p !== undefined) this.performanceReport = p;
        });

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
          this.radioHowToProceed = this.approverInformation.how_to_proceed;
          this.amountOfFundsDisbursed = this.approverInformation.amountOfFundsDisbursed;
          this.amountOfFundsRemaining = this.approverInformation.amountOfFundsRemaining;
          this.provideAnyRecommendations = this.approverInformation.provideAnyRecommendations;
          this.dateDisbursed = this.approverInformation.dateDisbursed;
        }
      }

      this.setReportsFromPartnerSetup();

      this.dtTrigger.next();
    }, error => console.log('Error getting reports', error));
  }

  setReportsFromPartnerSetup() {
    const params2 = new HttpParams().set('id', this.taskRecord.partnerSetupId);
    this.partnerSetupService.getPartnerSetupRecord(params2).subscribe(data => {
      if (data.setup != undefined && data.setup.setupValues != undefined) {

        let setupValues = JSON.parse(data.setup.setupValues);
        if (setupValues.currentStatus != undefined) this.totalGrantAmount = setupValues.currentStatus.totalAmountDisbursed;

        this.partnerSetupService.getSetupDisbursementPlanByPartnerSetupId(data.setup.id).subscribe((res: any) => {
          console.log("DisbursementPlan", res)
          res?.forEach((q) => {
            if (q.datePeriod == this.taskRecord.reportingPeriod) {
              this.totalAdvanced = q.disbursement
            }
          })
        })

        this.partnerSetupService.getSetupBudgetByPartnerSetupId(data.setup.id).subscribe((res: any) => {
          console.log("Budget", res)
          let tac = 0
          let tas = 0
          res?.forEach((b) => {
            tac += +b.approvedAmount;
            tas += +b.totalSpent;
            if (!this.financialReport.some(x => x.budgetLine === b.budgetLine)) {
              this.financialReport.push({
                budgetLine: b.budgetLine,
                approvedBudget: b.approvedAmount,
                expenseToDate: b.totalSpent
              });
            } else {
              this.financialReport.forEach((report) => {
                if (report.budgetLine == b.budgetLine) {
                  report.approvedBudget = b.approvedAmount
                  report.expenseToDate = b.totalSpent
                }
              });
            }
          });
          this.totalGrantAmount = tac
          this.totalAmountSpent = tas
          this.updateProjectOverview()
        })

        this.partnerSetupService.getSetupMilestonesByPartnerSetupId(data.setup.id).subscribe((res: any) => {
          console.log("Milestones", res)
          res?.forEach((i) => {
            let disaggregation = JSON.parse(i.disaggregation)
            let target = this.getTargetForThisQuarter(disaggregation);
            const params = new HttpParams()
              .set('name', i.name) //milestone name
              .set('partnerId', this.taskRecord.partnerId)
              .set('startDate', this.taskRecord.startDate)
              .set('endDate', this.taskRecord.endDate);
            this.projectMilestoneService.getMilestoneDataForReports(params).subscribe((milestone: any) => {
              if (milestone != undefined) {
                let cumulative = milestone.cumulativeAchievement ?? 0;
                let quarter = milestone.quaterAchievement ?? 0;
                let percentageAchievement: number;
                let p = (cumulative / quarter) * 100;
                if (p > 0 && isFinite(p)) {
                  percentageAchievement = p;
                } else {
                  percentageAchievement = 0;
                }

                if (!this.performanceReport.some(x => x.outputIndicators === i.name)) {
                  this.performanceReport.push({
                    milestoneId: i.milestoneId,
                    outputIndicators: i.name,
                    overallTarget: i.overallTarget,
                    cumulativeAchievement: cumulative,
                    quarterAchievement: quarter,
                    quarterTarget: target,
                    percentageAchievement: percentageAchievement
                  });
                } else {
                  this.performanceReport.forEach((report) => {
                    if (report.outputIndicators == i.name) {
                      report.milestoneId = i.milestoneId
                      report.overallTarget = i.overallTarget
                      report.cumulativeAchievement = cumulative
                      report.quarterAchievement = quarter
                      report.quarterTarget = target
                      report.percentageAchievement = percentageAchievement
                    }
                  });
                }
              }
            }, error => console.log(error));
          });
        })

        let reportValues: { [key: string]: string } = {};
        this.saveReport(reportValues, 'draft');
      }
    }, error => console.log(error));
  }

  getTargetForThisQuarter(disaggregation: any) {
    let value = 0;
    disaggregation.forEach((d) => {
      if (d.datePeriod == this.taskRecord.reportingPeriod) {
        value = d.target;
      }
    });
    return value;
  }

  setAttachments(params) {
    this.reportFormService.getAttachmentsForTask(params).subscribe(data => {
      if (data.files !== null) {
        data.files.forEach((file) => {
          if (file.name === 'attachment1') {
            this.attachment1 = file.path;
            this.shortLink1 = file.path;
          }
          if (file.name === 'attachment2') {
            this.attachment2 = file.path;
            this.shortLink2 = file.path;
          }
          if (file.name === 'attachment3') {
            this.attachment3 = file.path;
            this.shortLink3 = file.path;
          }
        });
      }
    }, error => console.log('Error getting attachments', error));
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

  onNewCommentsHandler(comment: CommentNode) {
    this.reviewerComments = comment.text
  }

  onNewRecommendationsHandler(comment: CommentNode) {
    this.reviewerRecommendations = comment.text
    this.provideAnyRecommendations = comment.text
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
    if (event.target.id === 'attachment1') {
      this.attachment1 = files.item(0).name;
    }
    if (event.target.id === 'attachment2') {
      this.attachment2 = files.item(0).name;
    }
    if (event.target.id === 'attachment3') {
      this.attachment3 = files.item(0).name;
    }
    this.uploadFile(files.item(0), event.target.id);
  }

  uploadFile(file, id) {
    this.loading = !this.loading;
    console.log(file);
    this.fileUploadService.upload(file, 'reporting-' + this.taskRecord.taskDefinitionKey).subscribe((data) => {
        if (id === 'attachment1') this.shortLink1 = data.path;
        if (id === 'attachment2') this.shortLink2 = data.path;
        if (id === 'attachment3') this.shortLink3 = data.path;
        this.loading = false;
      }
    );
  }

  changeForm(formName) {
    this.isSubmitVisible = false;
    this.isReviewVisible = false;
    this.isApproveVisible = false;
    if (formName == 'Submit') {
      this.isSubmitVisible = true;
    }
    if (formName == 'Review') {
      this.isReviewVisible = true;
    }
    if (formName == 'Approve') {
      this.isApproveVisible = true;
    }
    window.scroll(0, 0);
    this.success = false;
  }

  viewComments(): void {
    this.openCommentsPopup = !this.openCommentsPopup;
    this.openPopup = this.openCommentsPopup;
  }

  addComment() {
    let text = (document.getElementById('addComment') as HTMLTextAreaElement);
    if (text.value !== '') {
      this.comments.push(new CommentNode(uuid(), text.value, this.authService.getLoggedInUsername(), [], [], new Date()));
      text.value = '';
    }
  }

  viewRecommendations(): void {
    this.openRecommendationsPopup = !this.openRecommendationsPopup;
    this.openPopup = this.openRecommendationsPopup;
  }

  addRecommendation() {
    let text = (document.getElementById('addRecommendation') as HTMLTextAreaElement);
    if (text.value !== '') {
      this.recommendations.push(new CommentNode(uuid(), text.value, this.authService.getLoggedInUsername(), [], [], new Date()));
      text.value = '';
    }
  }

  get performance() {
    return this.performanceReport;
  }

  get financial() {
    return this.financialReport;
  }

  cellEditor(row, td_id, key: string, oldValue, type?: string) {
    new CellEdit().edit(row.id, td_id, oldValue, key, this.saveCellValue, type);
  }

  saveCellValue = (value: string, key: string, rowId): void => {
    if (value !== null && value !== undefined) {
      switch (key) {
        case 'summaryComment':
          if (this.performanceReport.some(x => x.id === rowId)) {
            this.performanceReport.forEach(function (item) {
              if (item.id === rowId) {
                item.commentOnResult = value;
              }
            });
          }
          break;
        case 'quarter_achievement':
          if (this.performanceReport.some(x => x.id === rowId)) {
            this.performanceReport.forEach(function (item) {
              if (item.id === rowId) {
                item.quarterAchievement = value;
              }
            });
          }
          break;
        case 'quarterExpense':
          if (this.financialReport.some(x => x.id === rowId)) {
            this.financialReport.forEach((item) => {
              if (item.id === rowId) {
                if (+value <= +item.totalAdvanced) {
                  item.quarterExpenses = value;
                } else {
                  this.alertService.error(`Quarter expense should be less than Total Advanced`);
                  return;
                }
                item.variance = +item.totalAdvanced - +value;
              }
            });
          }
          break;
        case 'totalAdvanced':
          if (this.financialReport.some(x => x.id === rowId)) {
            this.financialReport.forEach((item) => {
              if (item.id === rowId) {
                item.totalAdvanced = value;
                item.variance = +item.totalAdvanced - +item.quarterExpenses;
              }
            });
          }
          break;
        case 'reason_for_variance':
          if (this.financialReport.some(x => x.id === rowId)) {
            this.financialReport.forEach(function (item) {
              if (item.id === rowId) {
                item.reasonForVariance = value;
              }
            });
          }
          break;
      }
    }
    //update project overview values
    this.updateProjectOverview();
    //update report in DB
    let reportValues: { [key: string]: string } = {};
    this.saveReport(reportValues, 'draft');
  };

  saveReport(reportValues: { [key: string]: string }, status) {
    this.submitting = true

    let reportRecord: { [key: string]: string } = {
      taskId: this.taskRecord.id,
      processInstanceId: this.taskRecord.processInstanceId,
      partnerSetupId: this.taskRecord.partnerSetupId,
      partnerId: this.taskRecord.partnerId,
      userId: this.authService.getLoggedInUsername(),
      groupId: this.taskRecord.groupId,
      taskDefinitionKey: this.taskRecord.taskDefinitionKey,
      reportValues: JSON.stringify(reportValues),
      status: status
    };

    const params = new HttpParams().set('processInstanceId', this.taskRecord.processInstanceId);
    this.reportFormService.getReportForTask(params).subscribe(data => {
      if (data.report !== null && data.report !== undefined) {
        this.reportFormService.updateReport(reportRecord, data.report.id).subscribe((data) => {
          this.saveFinancialReport(data.id)
          this.savePerformanceReport(data.id)
          this.error = false;
          this.success = true;
          this.successMessage = 'Updated Report';
          this.updateTaskStatus(status);
        }, error => {
          this.error = true;
          this.errorMessage = 'Failed to update Report';
          this.success = false;
          console.log(error);
        });
      } else {
        this.reportFormService.createReport(reportRecord).subscribe((data) => {
          this.saveFinancialReport(data.id)
          this.savePerformanceReport(data.id)
          this.error = false;
          this.success = true;
          this.successMessage = 'Saved Report';
          this.updateTaskStatus(status);
        }, error => {
          this.error = true;
          this.errorMessage = 'Failed to save Report';
          this.success = false;
          console.log(error);
        });
      }
    });
    setTimeout(() => {
      this.submitting = false;
      if (status != 'draft') {
        this.router.navigate(['/home']);
      }
      this.success = false;
      this.error = false;
    }, 3000);
  }

  saveFinancialReport(reportId) {
    this.financialReport.forEach((fr) => {
      let formData: { [key: string]: string } = {
        reportId: reportId,
        budgetLine: fr.budgetLine,
        approvedBudget: fr.approvedBudget,
        expenseToDate: fr.expenseToDate,
        totalAdvanced: fr.totalAdvanced,
        variance: fr.variance,
        quarterExpenses: fr.quarterExpenses,
        reasonForVariance: fr.reasonForVariance
      };

      this.reportFormService.createFinancialReport(formData).subscribe((res) => {
        // console.log("Updated financial report", res)
      })
    })
  }

  savePerformanceReport(reportId) {
    this.performanceReport.forEach((pr) => {
      let formData: { [key: string]: string } = {
        reportId: reportId,
        milestoneId: pr.milestoneId,
        outputIndicators: pr.outputIndicators,
        overallTarget: pr.overallTarget,
        cumulativeAchievement: pr.cumulativeAchievement,
        quarterAchievement: pr.quarterAchievement,
        quarterTarget: pr.quarterTarget,
        percentageAchievement: pr.percentageAchievement,
        commentOnResult: pr.commentOnResult,
      };

      this.reportFormService.createPerformanceReport(formData).subscribe((res) => {
        // console.log("Updated performance report", res)
      })
    })
  }

  submitReport(status) {
    this.error = false;
    this.success = false;

    if (status != 'save') {
      this.financialReport.forEach((fr) => {
        let financialReportAllFilled = Validator.validateJSON(fr, ['totalAdvanced', 'quarterExpenses', 'reasonForVariance'])
        if (!financialReportAllFilled) {
          this.submitting = false;
          this.alertService.error("Please fill in all compulsory fields in Financial Report");
          return
        }
      })

      this.performanceReport.forEach((pr) => {
        let performanceReportAllFilled = Validator.validateJSON(pr, ['commentOnResult'])
        if (!performanceReportAllFilled) {
          this.submitting = false;
          this.alertService.error("Please fill in all compulsory fields in Performance Report");
          return
        }
      })

      if (this.attachment1 == undefined) {
        this.submitting = false;
        this.alertService.error("Please provide attachment");
        return
      }

      if (this.attachment2 == undefined) {
        this.submitting = false;
        this.alertService.error("Please provide attachment");
        return
      }
    }

    let reportValues: { [key: string]: string } = {
      reviewerInformation: JSON.stringify(this.reviewerInformation),
      approverInformation: JSON.stringify(this.approverInformation)
    };

    let attachments = [];
    attachments.push({key: 'attachment1', value: this.shortLink1});
    attachments.push({key: 'attachment2', value: this.shortLink2});
    attachments.push({key: 'attachment3', value: this.shortLink3});

    attachments.forEach((attachment) => {
      if (attachment != null) {
        let fileRecord: { [key: string]: string } = {
          taskId: this.taskRecord.id,
          processInstanceId: this.taskRecord.processInstanceId,
          userId: this.taskRecord.partnerId,
          groupId: this.taskRecord.groupId,
          taskDefinitionKey: this.taskRecord.taskDefinitionKey,
          path: attachment.value,
          name: attachment.key,
        };

        const params = new HttpParams().set('taskId', this.recordId).set('name', attachment.key);
        this.reportFormService.getFileByTaskAndName(params).subscribe((data) => {
          let record = data.fileRecord;
          if (record == null) {
            this.reportFormService.saveFile(fileRecord).subscribe((data) => {
              console.log('saved file successfully');
            }, error => console.log('fileError', error));
          } else {
            this.reportFormService.updateFile(fileRecord, record.id).subscribe((data) => {
              console.log('updated file successfully');
            }, error => console.log('fileError', error));
          }
        }, error => console.log(error));

      }
    });

    if (status === 'save') {
      this.saveReport(reportValues, 'draft');
    }
    if (status === 'submit') {
      this.saveReport(reportValues, 'completed');
    }
  }

  reviewReport(status) {
    this.error = false;
    this.success = false;

    this.saveCommentsAndRecommendations()
    let reviewerInfo = {
      expenses_realistic: this.radioExpensesRealistic,
      attachments_verified: this.radioAttachmentsVerified,
      figures_realistic: this.radioFiguresRealistic,
      narrative_align: this.radioNarrativeAlign,
      inline_with_targets: this.radioInlineWithTargets,
      evidence_satisfactory: this.radioEvidenceSatisfactory,
      comments: this.reviewerComments,
      recommendations: this.reviewerRecommendations
    }

    let allFilled = Validator.validateJSON(reviewerInfo, ['expenses_realistic',
      'attachments_verified',
      'figures_realistic',
      'narrative_align',
      'inline_with_targets',
      'evidence_satisfactory',
      'comments',
      'recommendations'])
    if (!allFilled && status == "submit") {
      this.submitting = false;
      this.alertService.error("Please fill in all required fields");
      return
    }

    let reportValues: { [key: string]: string } = {
      approverInformation: JSON.stringify(this.approverInformation),
      reviewerInformation: JSON.stringify(reviewerInfo)
    };

    if (status === 'revise') {
      this.saveReport(reportValues, 'draft');
    }
    if (status === 'submit') {
      this.saveReport(reportValues, 'completed');
    }
  }

  approveReport() {
    this.error = false;
    this.success = false;

    this.saveCommentsAndRecommendations()
    let approverInfo = {
      suggested_changes_satisfactory: this.radioSuggestedChangesSatisfactory,
      reports_well_aligned: this.radioReportsWellAligned,
      recommend_fund: this.radioRecommendFund,
      end_of_partnership: this.radioEndOfPartnership,
      how_to_proceed: this.radioHowToProceed,
      amountOfFundsDisbursed: this.amountOfFundsDisbursed,
      amountOfFundsRemaining: this.amountOfFundsRemaining,
      dateDisbursed: this.dateDisbursed,
      provideAnyRecommendations: this.provideAnyRecommendations
    }

    let allFilled

    if (this.isApproveFundDisbursement) {
      allFilled = Validator.validateJSON(approverInfo, [
        'how_to_proceed',
        'provideAnyRecommendations'])
    }

    if (this.isDisburseFunds) {
      allFilled = Validator.validateJSON(approverInfo, [
        'amountOfFundsDisbursed',
        'dateDisbursed',
        'amountOfFundsRemaining'])
    }

    if (this.isApproveVisible) {
      if (this.radioRecommendFund == 'Yes') {
        allFilled = Validator.validateJSON(approverInfo, [
          'suggested_changes_satisfactory',
          'reports_well_aligned',
          'recommend_fund',
          'end_of_partnership',
          'amountOfFundsDisbursed',
          'provideAnyRecommendations'])
      } else {
        allFilled = Validator.validateJSON(approverInfo, [
          'suggested_changes_satisfactory',
          'reports_well_aligned',
          'recommend_fund',
          'end_of_partnership',
          'provideAnyRecommendations'])
      }
    }

    if (!allFilled) {
      this.submitting = false;
      this.alertService.error("Please fill in all required fields");
      return
    }

    let reportValues: { [key: string]: string } = {
      reviewerInformation: JSON.stringify(this.reviewerInformation),
      approverInformation: JSON.stringify(approverInfo)
    };

    this.saveReport(reportValues, 'completed');
  }

  saveCommentsAndRecommendations() {
    this.comments.push(new CommentNode(uuid(), this.reviewerComments, this.authService.getLoggedInUsername(), [], [], new Date()));

    this.comments.forEach((comment) => {
      let commentsRecord: { [key: string]: string } = {
        taskId: this.taskRecord.id,
        processInstanceId: this.taskRecord.processInstanceId,
        userId: comment.user,
        groupId: this.taskRecord.groupId,
        taskDefinitionKey: this.taskRecord.taskDefinitionKey,
        content: comment.text,
        children: JSON.stringify(comment.answers),
      };
      const params = new HttpParams().set('id', comment.id);
      this.reportFormService.getComment(params).subscribe((data) => {
        if (data.comment === null || data.comment === undefined) {
          this.reportFormService.saveComment(commentsRecord).subscribe((data) => {
            console.log('saved comment successfully');
          }, error => console.log('save comment', error));
        }
      }, error => console.log('comment', error));
    });

    this.recommendations.push(new CommentNode(uuid(), this.reviewerRecommendations, this.authService.getLoggedInUsername(), [], [], new Date()));
    this.recommendations.push(new CommentNode(uuid(), this.provideAnyRecommendations, this.authService.getLoggedInUsername(), [], [], new Date()));

    this.recommendations.forEach((recommendation) => {
      let recommendationsRecord: { [key: string]: string } = {
        taskId: this.taskRecord.id,
        processInstanceId: this.taskRecord.processInstanceId,
        userId: recommendation.user,
        groupId: this.taskRecord.groupId,
        taskDefinitionKey: this.taskRecord.taskDefinitionKey,
        content: recommendation.text,
      };
      const params = new HttpParams().set('id', recommendation.id);
      this.reportFormService.getRecommendation(params).subscribe((data) => {
        if (data.recommendation === null || data.recommendation === undefined) {
          this.reportFormService.saveRecommendation(recommendationsRecord).subscribe((data) => {
            console.log('saved recommendation successfully');
          }, error => console.log('save recommendation', error));
        }
      }, error => console.log('recommendation', error));
    });
  }

  updateProjectOverview() {
    let tac = 0
    let tas = 0
    let tad = 0
    let tb = 0
    this.financialReport.forEach(b => {
      tac += +b.approvedBudget;
      if (b.expenseToDate != undefined) tas += (+b.expenseToDate + +b.quarterExpenses); else tas += +b.quarterExpenses;
      tad += +b.totalAdvanced;
      tb += +b.variance
    })
    this.totalGrantAmount = tac
    this.totalAmountSpent = tas
    this.totalAdvanced = tad
    this.balance = tb
  }

  updateTaskStatus(status) {
    this.taskRecord.status = status;
    if (this.taskRecord.taskDefinitionKey === 'Submit_Report') {
      this.taskRecord.outputVariables = '{}';
    }
    if (this.taskRecord.taskDefinitionKey === 'Approve_Fund_Disbursement') {
      this.taskRecord.outputVariables = '{"Approve_Funding": "' + this.radioHowToProceed + '"}';
    }
    if (this.taskRecord.taskDefinitionKey === 'Approve_Report') {
      this.taskRecord.outputVariables = '{"Funding_Decision": "' + this.radioRecommendFund + '"}';
    }

    if (status == 'completed') {
      if (this.taskRecord.taskDefinitionKey === 'Disburse_Funds') {
        this.updateCalendarStatus();
      }
      if (this.taskRecord.taskDefinitionKey === 'Approve_Report' && this.radioRecommendFund == 'Yes') {
        this.updateCalendarStatus();
      }
      if (this.taskRecord.taskDefinitionKey === 'Approve_Fund_Disbursement' && this.radioHowToProceed == 'No') {
        this.updateCalendarStatus();
      }
    }

    this.taskListService.updateTask(this.taskRecord, this.taskRecord.id).subscribe((data) => {
      console.log('successfully updated task');
    }, error => console.log('update task', error));
  }

  updateCalendarStatus() {
    const params = new HttpParams().set('setupId', this.taskRecord.partnerSetupId).set('completed', "yes");
    this.partnerSetupService.updateReportingCalendarStatus(params).subscribe((data) => {
      console.log('updated calendar status')
    }, error => console.log('failed update calendar status', error));
  }

  onBackPressed() {
    this.router.navigate(['/home']);
  }
}
