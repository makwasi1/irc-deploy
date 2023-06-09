import {Component, OnInit, ViewChild} from '@angular/core';
import {Subject} from "rxjs";
import {v4 as uuid} from 'uuid';
import {PartnerSetupService} from "../../services/partner-setup.service";
import {ProgramPartnersService} from "../../services/program-partners.service";
import {CellEdit, OnUpdateCell} from '../../helpers/cell-edit';
import {Location} from "@angular/common";
import {AuthService} from "../../services/auth.service";
import {DateSplitter} from '../../helpers/date-splitter';
import {HttpParams} from "@angular/common/http";
import {ActivatedRoute, Router} from "@angular/router";
import {ProjectMilestoneService} from "../../services/project-milestone.service";
import {Indicator} from "../../models/indicator";
import {AlertService} from "../../services/alert";

@Component({
  selector: 'app-partner-setup',
  templateUrl: './partner-setup.component.html',
  styleUrls: ['./partner-setup.component.css']
})
export class PartnerSetupComponent implements OnInit, OnUpdateCell {

  dtOptions: any = {};
  dtTrigger: Subject<any> = new Subject<any>();

  calendar: any = {};
  budget: any = [];
  totalApprovedAmount = 0;
  totalBudgetDisburse = 0;
  totalDisbursement = 0;
  startReportingCycle: boolean = false;
  disbursementPlan: any = [];
  currentStatus: any = {};
  indicators: Indicator[] = [];

  setup: any;
  indicatorForDisaggregation: Indicator;
  organisationalInfo: any = [];
  listOfPartners: any = [];
  milestones: any = [];
  partnerChosen: string;
  programChosen: string;
  partnerSetupId: string;

  openPopup: boolean;
  editing: boolean;
  error: boolean;
  success: boolean;
  errorMessage: string;
  successMessage: string;
  showDisaggregation: boolean;
  btn_id: string;

  periodItems = [
    {name: 'Biannual', value: 'biannual'},
    {name: 'Quarterly', value: 'quarter'},
    {name: 'Annually', value: 'annual'}
  ];

  constructor(private router: Router, private route: ActivatedRoute,
              private location: Location,
              private partnerSetupService: PartnerSetupService,
              public authService: AuthService,
              private programPartnersService: ProgramPartnersService,
              private projectMilestoneService: ProjectMilestoneService,
              private alertService: AlertService) {
  }

  ngOnInit(): void {
    this.editing = false;
    this.route.params
      .subscribe(p => {
        this.partnerSetupId = p['id'];
        const params = new HttpParams().set('id', this.partnerSetupId);
        this.partnerSetupService.getPartnerSetupRecord(params).subscribe(data => {
          this.editing = true;
          this.setPartnerSetupInfo(data.setup);
          this.calendar = {
            periodType: data.setup.periodType,
            grantStartDate: data.setup.startDate,
            grantEndDate: data.setup.endDate,
            projectReportingStartDate: data.setup.reportingStartDate,
            reportingCalender: this.getCalendarForSetup(data.setup.id)
          };
        }, error => console.log(error));
      });

    this.projectMilestoneService.getMilestones().subscribe((data) => {
      if (data !== null && data !== undefined) {
        this.milestones = data;
      }
    });

    if (this.partnerSetupId != undefined) {
      this.programPartnersService.getProgramPartners().subscribe(data => {
        if (data !== null && data !== undefined) {
          this.listOfPartners = data;
        }
      });
    } else {
      this.programPartnersService.getProgramPartnersWithoutWorkPlan().subscribe(data => {
        if (data !== null && data !== undefined) {
          this.listOfPartners = data;
        }
      });
    }

    this.dtOptions = {
      pagingType: "numbers",
      lengthMenu: [[5, 10, 25, 50, -1], [5, 10, 25, 50, "All"]],
      processing: true,
      responsive: true,
      dom: 'lfBrtip',
      buttons: []
    };
  }

  setPartnerSetupInfo(data) {
    this.setup = data;
    if (data !== null && data !== undefined) {
      let setupValues = JSON.parse(data.setupValues);
      this.partnerChosen = data.partnerId;
      this.startReportingCycle = data.startCycle == "true";
      if (this.partnerChosen != undefined) this.onPartnerChange()

      this.partnerSetupService.getSetupBudgetByPartnerSetupId(data.id).subscribe((res: any) => {
        this.budget = res;
        this.budget.forEach((data) => {
          this.updateBudgetAmount(data.id, data.approvedAmount);
          this.updateBudgetDisburse(data.id, data.totalSpent);
        });
      })

      this.partnerSetupService.getSetupMilestonesByPartnerSetupId(data.id).subscribe((res: any) => {
        res?.forEach((data) => {
          if (!this.indicators.some(x => x.name === data.name)) {
            this.indicators.push({
              id: data.id,
              name: data.name,
              milestoneId: data.milestoneId,
              overallTarget: data.overallTarget,
              disaggregation: JSON.parse(data.disaggregation)
            });
          }
        });
      })

      this.partnerSetupService.getSetupDisbursementPlanByPartnerSetupId(data.id).subscribe((res: any) => {
        this.disbursementPlan = res;
        this.disbursementPlan.forEach((data) => {
          this.updateDisbursementPlanValues(data.id, data.disbursement);
        });
      })

      if (setupValues.currentStatus != undefined) this.currentStatus = setupValues.currentStatus;
    }

    this.dtTrigger.next();
  }

  getCalendarForSetup(id): any {
    let reportingCalendar: { [key: string]: string }[] = [];
    const params = new HttpParams().set('setupId', id);
    this.partnerSetupService.getReportingCalendarByPartnerSetupId(params).subscribe(data => {
      data.calendar.forEach((cal) => {
        reportingCalendar.push({
          id: cal.id,
          startDate: cal.startDate,
          endDate: cal.endDate,
          datePeriod: cal.period
        });
      });
    });
    return reportingCalendar
  }

  generateCalendar(event) {
    if (this.disbursementPlan.length > 0 || this.indicators.length > 0 || this.budget.length > 0) {
      if (confirm('This action will clear the targets and disbursement entered')) {
        this.disbursementPlan = [];
        this.indicators = [];
        this.budget = [];
        this.calendarDates();
      }
    } else {
      this.calendarDates();
    }
  }

  calendarDates() {
    let startDate = this.calendar.projectReportingStartDate;
    let endDate = this.calendar.grantEndDate;

    if (this.calendar.periodType == "quarter") {
      //generate quarters
      this.calendar.reportingCalender = DateSplitter.genDatesInRange(startDate, endDate, false);
    } else if (this.calendar.periodType == "biannual") {
      //generate biannual
      let years = [];
      let months = DateSplitter.genDatesInRange(startDate, endDate, true);
      let numOfYears = Math.floor(months.length / 6);
      let countStart = 0;
      let countEnd = 0;
      for (let number = 1; number <= numOfYears; number++) {
        countStart = countEnd + (number - 1);
        countEnd = countStart + 5;
        years.push({
          'datePeriod': 'HY' + number,
          'startDate': months[countStart].startDate,
          'endDate': months[countEnd].endDate,
        });
      }
      this.calendar.reportingCalender = years;
    } else if (this.calendar.periodType == "annual") {
      //generate two years
      let years = [];
      let months = DateSplitter.genDatesInRange(startDate, endDate, true);
      let numOfYears = Math.floor(months.length / 12);
      let countStart = 0;
      let countEnd = 0;
      for (let number = 1; number <= numOfYears; number++) {
        countStart = countEnd + (number - 1);
        countEnd = countStart + 11;
        years.push({
          'datePeriod': 'Y' + number,
          'startDate': months[countStart].startDate,
          'endDate': months[countEnd].endDate,
        });
      }
      this.calendar.reportingCalender = years;
    }

    //set disbursement
    this.calendar.reportingCalender.forEach((period) => {
      this.disbursementPlan.push(
        {
          id: uuid(),
          datePeriod: period.datePeriod,
          startDate: period.startDate,
          endDate: period.endDate,
          disbursement: ''
        }
      );
    });
    if (this.setup != undefined) this.saveReportingCalendar(this.setup.id);
  }

  onPartnerChange() {
    if (this.partnerChosen != undefined) {
      this.programPartnersService.getCurrentProgramPartner(this.partnerChosen).subscribe((results: any) => {
        if (results !== null && results !== undefined) {
          this.organisationalInfo = results;
          this.programChosen = results.programId;
        }
      });
    }
  }

  createNewIndicator() {
    if (this.calendar.reportingCalender == undefined) {
      alert('No Calendar dates, Fill in reporting calendar');
      return;
    }

    let id = uuid();
    this.indicators.push({id: id, name: '', milestoneId: '', overallTarget: '', disaggregation: []});
  }

  setDisaggregation(rowId) {
    if (this.indicators.some(x => x.id === rowId)) {
      this.indicators.forEach((item) => {
        this.calendar.reportingCalender.forEach((c) => {
          let exists = false;
          if (item.disaggregation.some(x => x.datePeriod === c.datePeriod)) exists = true;
          if (!exists) item.disaggregation.push(
            {
              datePeriod: c.datePeriod,
              target: ''
            }
          );
        });
        if (item.id === rowId) this.createNewBudgetItem(item);
      });
    }
  }

  createNewBudgetItem(indicator: Indicator) {
    if (this.budget.some(x => x.milestoneId === indicator.id)) {
      this.budget.forEach(function (item) {
        if (item.milestoneId === indicator.id) item.budgetLine = indicator.name;
      });
    } else {
      let id = uuid();
      this.budget.push({id: id, milestoneId: indicator.id, budgetLine: indicator.name, approvedAmount: ''});
    }
  }

  addNewBudgetItem() {
    let id = uuid();
    this.budget.push({id: id, milestoneId: '', budgetLine: '', approvedAmount: ''});
    this.setDisaggregation(id);
  }

  toggleDisaggregation(btn_id, data) {
    this.showDisaggregation = !this.showDisaggregation;
    this.openPopup = this.showDisaggregation;
    this.btn_id = btn_id;
    const button = (document.getElementById(btn_id) as HTMLButtonElement);

    const minus_icon = document.createElement('i');
    minus_icon.classList.add('text', 'fas', 'fa-minus');
    minus_icon.style.fontSize = '20px';
    minus_icon.style.color = 'red';

    const plus_icon = document.createElement('i');
    plus_icon.classList.add('text', 'fas', 'fa-plus');
    plus_icon.style.fontSize = '20px';

    if (this.showDisaggregation) {
      button.firstChild.replaceWith(minus_icon);
      //set disaggregation values
      this.indicatorForDisaggregation = data;
    } else {
      button.firstChild.replaceWith(plus_icon);
    }
  }

  removeIndicator(indicator: Indicator) {
    //delete from db
    this.budget.forEach((data) => {
      if (data.budgetLine === indicator.name) this.partnerSetupService.deletePartnerBudget(data.id).subscribe(res=> console.log("delete budget"))
    });
    this.partnerSetupService.deletePartnerMilestone(indicator.id).subscribe(res=> console.log("delete milestone"))
    //remove from JSON object list
    this.indicators = this.indicators.filter(item => item.id != indicator.id);
    this.budget = this.budget.filter(item => item.budgetLine != indicator.name);
    //update budget amounts
    this.budget.forEach((data) => {
      this.updateBudgetAmount(data.id, data.approvedAmount);
    });
  }

  cellEditor(rowId, tdId, key: string, oldValue, type?: string, selectList?: []) {
    new CellEdit().edit(rowId, tdId, oldValue, key, this.saveCellValue, type, '', selectList);
  }

  saveCellValue = (value: string, key: string, rowId, extras): void => {
    if (value !== null && value !== undefined)
      switch (key) {
        case 'approved_amt':
          this.updateBudgetAmount(rowId, value);
          break;
        case 'total_spent':
          this.updateBudgetDisburse(rowId, value, true);
          break;
        case 'disbursementPlan':
          this.updateDisbursementPlanValues(rowId, value);
          break;
        case 'budget_line':
          if (this.budget.some(x => x.id === rowId)) {
            this.budget.forEach(function (item) {
              if (item.id === rowId) item.budgetLine = value;
            });
          }
          break;
        case 'disaggregation':
          let overallTarget: number = 0;
          let arr = rowId.split(' ');
          let datePeriod = arr[0];
          let milestoneId = arr[1];
          this.indicators.forEach((indicator) => {
            if (indicator.id === milestoneId) {
              if (indicator.disaggregation.some(x => x.datePeriod === datePeriod)) {
                indicator.disaggregation.forEach(function (item) {
                  if (item.datePeriod === datePeriod) item.target = value
                  if (item.target.length != 0) overallTarget += +item.target;
                });
                indicator.overallTarget = overallTarget.toString();
                this.indicatorForDisaggregation = indicator;
              }
            }
          });
          break;
        case 'indicators':
          if (this.indicators.some(x => x.id === rowId)) {
            this.indicators.forEach(function (item) {
              if (item.id === rowId) {
                item.name = value
                item.milestoneId = extras;
              }
            });
          }
          this.setDisaggregation(rowId);
          break;
      }
    this.savePlan();
  }

  savePlan(done?: boolean) {
    this.error = false;
    this.success = false;

    let values: { [key: string]: string } = {
      currentStatus: this.currentStatus
    }

    let partnerSetupRecord: { [key: string]: string } = {
      userId: this.authService.getLoggedInUsername(),
      partnerId: this.partnerChosen,
      programId: this.programChosen,
      setupValues: JSON.stringify(values),
      startDate: this.calendar.grantStartDate,
      endDate: this.calendar.grantEndDate,
      reportingStartDate: this.calendar.projectReportingStartDate,
      periodType: this.calendar.periodType,
      startCycle: "" + this.startReportingCycle,
    }

    if (this.setup) {
      this.partnerSetupService.updatePartnerSetup(partnerSetupRecord, this.setup.id).subscribe((data) => {
        this.savePartnerSetupBudget(data.id)
        this.savePartnerSetupMilestones(data.id)
        this.savePartnerSetupDisbursementPlan(data.id)
        this.setPartnerSetupInfo(data);
        this.error = false;
        this.success = true;
        this.successMessage = "Updated Partner Setup";
      }, error => {
        this.error = true;
        this.errorMessage = "Failed to update Partner Setup";
        this.success = false;
        console.log(error);
      });
    } else {
      this.partnerSetupService.createPartnerSetup(partnerSetupRecord).subscribe((data) => {
        if (data !== null && data !== undefined) this.saveReportingCalendar(data.id);
        this.savePartnerSetupBudget(data.id)
        this.savePartnerSetupMilestones(data.id)
        this.savePartnerSetupDisbursementPlan(data.id)
        this.setPartnerSetupInfo(data);
        this.error = false;
        this.success = true;
        this.successMessage = "Saved Partner Setup";
      }, error => {
        this.error = true;
        this.errorMessage = "Failed to save Partner Setup";
        this.success = false;
        console.log(error);
      });
    }

    setTimeout(() => {
      if (done == true && this.success) this.onBackPressed();
      this.success = false;
      this.error = false;
    }, 3000);
  }

  savePartnerSetupBudget(partnerSetupId) {
    this.budget.forEach((budget) => {
      let formData: { [key: string]: string } = {
        partnerSetupId: partnerSetupId,
        budgetLine: budget.budgetLine,
        milestoneId: budget.milestoneId,
        approvedAmount: budget.approvedAmount,
        totalSpent: budget.totalSpent,
      };

      this.partnerSetupService.createPartnerSetupBudget(formData).subscribe((res) => {
        //console.log("Response", res)
      })
    })
  }

  savePartnerSetupMilestones(partnerSetupId) {
    this.indicators.forEach((indicator) => {
      let formData: { [key: string]: string } = {
        partnerSetupId: partnerSetupId,
        name: indicator.name,
        milestoneId: indicator.id,
        overallTarget: indicator.overallTarget,
        disaggregation: JSON.stringify(indicator.disaggregation),
      };
      this.partnerSetupService.createPartnerSetupMilestones(formData).subscribe((res) => {
        //console.log("Response", res)
      })
    })
  }

  savePartnerSetupDisbursementPlan(partnerSetupId) {
    this.disbursementPlan.forEach((disbursement) => {
      let formData: { [key: string]: string } = {
        partnerSetupId: partnerSetupId,
        datePeriod: disbursement.datePeriod,
        startDate: disbursement.startDate,
        endDate: disbursement.endDate,
        disbursement: disbursement.disbursement,
      };

      this.partnerSetupService.createPartnerSetupDisbursementPlan(formData).subscribe((res) => {
        //console.log("Response", res)
      })
    })

  }

  saveReportingCalendar(setupId) {
    let values: { [key: string]: any }[] = []
    this.calendar.reportingCalender.forEach((c) => {
      values.push({
        startDate: c.startDate,
        endDate: c.endDate,
        period: c.datePeriod,
        partnerSetupId: setupId,
        started: false,
        completed: false
      });
    });

    if (values.length != 0) {
      this.partnerSetupService.deleteReportingCalendarForPartner(setupId).subscribe((data) => {
        values.forEach((value) => {
          this.partnerSetupService.createReportingCalendar(value).subscribe((data) => {
            console.log("saved reporting calendar", data);
            if (this.calendar.reportingCalender.some(x => x.id === data.id)) {
              this.calendar.reportingCalender.forEach(function (item) {
                if (item.id === data.id) {
                  item.startDate = data.startDate;
                  item.endDate = data.endDate;
                  item.datePeriod = data.period;
                }
              });
            }
          }, error => {
            console.log(error);
          });
        });
      }, error => {
        console.log(error);
      });
    }
  }

  isValidJSONStr(str) {
    try {
      JSON.parse(str);
    } catch (e) {
      return false;
    }
    return true;
  }

  onBackPressed() {
    this.location.back();
  }

  private updateBudgetAmount(id, newValue) {
    let total: number = 0;
    if (this.budget.some(x => x.id === id)) {
      this.budget.forEach(function (item) {
        if (item.id === id) item.approvedAmount = newValue;
        total += +item.approvedAmount;
      });
    }
    this.totalApprovedAmount = total;
  }

  private updateBudgetDisburse(id, newValue, editing?: boolean) {
    let total: number = 0;
    if (this.budget.some(x => x.id === id)) {
      this.budget.forEach((item) => {
        if (item.id === id) {
          if (editing) {
            if (+newValue <= +item.approvedAmount) {
              item.totalSpent = newValue;
            } else {
              this.alertService.error(`Amount spent should be less than Amount Approved`);
              return;
            }
          } else item.totalSpent = newValue;
        }
        total += +item.totalSpent;
      });
    }
    this.totalBudgetDisburse = total;
    this.currentStatus.totalAmountDisbursed = total;
  }

  private updateDisbursementPlanValues(id, newValue) {
    let totalD: number = 0;
    if (this.disbursementPlan.some(x => x.id === id)) {
      this.disbursementPlan.forEach(function (item) {
        if (item.id === id) item.disbursement = newValue
        totalD += +item.disbursement;
      });
    }
    this.totalDisbursement = totalD
  }
}
