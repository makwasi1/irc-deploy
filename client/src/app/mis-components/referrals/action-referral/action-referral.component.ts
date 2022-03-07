import {Component, OnInit} from '@angular/core';
import {
  FormBuilder,
  FormGroup
} from '@angular/forms';
import {ActivatedRoute, Router} from '@angular/router';
import {AuthService} from '../../../services/auth.service';
import {UsersService} from "../../../services/users.service";
import {AlertService} from "../../../services/alert";
import {ReferralsService} from "../../../services/referrals.service";
import {CountriesService} from "../../../services/countries.service";
import {parseDate} from "devextreme/localization";
import {format} from "d3";
import {DatePipe} from "@angular/common";


@Component({
  selector: 'app-createReferral',
  templateUrl: './action-referral.component.html',
  styleUrls: ['./action-referral.component.scss']
})
export class ActionReferralComponent implements OnInit {
  private referrals: any;
  private nationalityValue = '';
  private followUpValue = '';

  constructor(
    private userService: UsersService,
    private datePipe: DatePipe,
    private CountriesService: CountriesService,
    private referralsService: ReferralsService,
    private alertService: AlertService,
    private authService: AuthService,
    private formBuilder: FormBuilder,
    private route: ActivatedRoute,
    private router: Router
  ) {
  }

  clicked = false;
  currentDashboards: any
  formGroup: FormGroup
  submitted = false;
  received_feedback = [
    {
      'name': 'Yes'
    },
    {
      'name': 'No'
    },
    {
      'name': 'Not Known'
    },
  ];
  nationality_status = [
    {
      'name': 'Foreigner'
    },
    {
      'name': 'Refugee'
    },
    {
      'name': 'National'
    }
  ];
  followup_needed = [
    {
      'name': 'Yes'
    },
    {
      'name': 'No'
    },
  ];
  followup_areas = [
    {
      'name': 'Follow Up Area 1'
    },
    {
      'name': 'Follow Up Area 2'
    },
    {
      'name': 'Follow Up Area 3'
    },
    {
      'name': 'Follow Up Area 4'
    },
    {
      'name': 'Follow Up Area 5'
    },

  ];
  reason_for_referral = [
    {
      'name': 'Food amd Shelter'
    },
    {
      'name': 'Formal Education'
    },
    {
      'name': 'Insecurity'
    },
    {
      'name': 'Resettlement'
    },
    {
      'name': 'LGBTI'
    },
  ];
  age_category = [
    {
      'name': '0 - 28 days'
    },
    {
      'name': '29 days - 4 years'
    },
    {
      'name': '5 - 9 years'
    },
    {
      'name': '10 -19 years'
    },
    {
      'name': '20 - 29 years'
    },
    {
      'name': '30 - 59 years'
    },
    {
      'name': '60 years and above'
    },
  ];
  organization_referred_to = [
    {
      'name': 'AVSI FOUNDATION'
    },
    {
      'name': 'JRS'
    },
    {
      'name': 'REFUGEPOINT'
    },
  ];
  identification_document = [
    {
      'name': 'National ID'
    },
    {
      'name': 'Alien Card'
    },
    {
      'name': 'Asylum Card'
    },
    {
      'name': 'Waiting card'
    },
    {
      'name': 'UNHCR Mandate'
    },
    {
      'name': 'Minors Pass'
    },
  ];
  country_of_origin: any;

  get f() {
    return this.formGroup.controls;
  }

  ngOnInit(): void {
    this.CountriesService.getCountries().subscribe(data => {
      this.country_of_origin = data
    }, error => {
      this.alertService.error("Failed to get Countries")
    })
    this.referralsService.getCurrentReferral(this.route.snapshot.params.id).subscribe(data => {
      console.log(data, "referral data")

      this.referrals = data
      this.formGroup = this.formBuilder.group({
        dateOfReferral: [(this.datePipe.transform(this.referrals.dateOfReferral, 'yyyy-MM-dd'))],
        nameOfReferringOfficer: [this.referrals?.nameOfReferringOfficer],
        nameOfClientBeingReferred: [this.referrals?.nameOfClientBeingReferred],
        phoneNumber: [this.referrals?.phoneNumber],
        dateOfBirth: [this.datePipe.transform(this.referrals.dateOfBirth, 'yyyy-MM-dd')],
        ageCategory: [this.referrals?.ageCategory],
        countryOfOrigin: [this.referrals?.countryOfOrigin],
        identificationDocument: [this.referrals?.identificationDocument],
        identificationNumber: [this.referrals?.identificationNumber],
        reasonForReferral: [this.referrals?.reasonForReferral],
        organizationReferredTo: [this.referrals?.organizationReferredTo],
        receivedFeedback: [this.referrals?.receivedFeedback],
        feedbackGiven: [this.referrals?.feedbackGiven],
        dateOfFeedback: [this.datePipe.transform(this.referrals.dateOfFeedback, 'yyyy-MM-dd')],
        nationalityStatus: [this.referrals?.nationalityStatus],
        followupNeeded: [this.referrals?.followupNeeded],
        followupAreas: [this.referrals?.followupAreas],
        followupOrganization: [this.referrals?.followupOrganization],
        disability: [this.referrals?.disability],
        status: ['Actioned'],
      });

    })
  }

  actionReferral() {
    this.clicked = true;
    this.submitted = true;
    if (this.formGroup.invalid) {
      console.log('Invalid');
      return;
    }
    const submitData = this.formGroup.value;
    console.log(submitData)
    this.referralsService.updateReferral(this.route.snapshot.params.id, submitData).subscribe((result) => {
      console.warn(result, 'Referral Updated Successfully');
      this.alertService.success(`Referral has been successfully updated`)
      this.router.navigate(['/referrals-list']);
    }, error => {
      this.alertService.error(`Failed to update Referral`)
    });
  }

  close() {
    this.router.navigate(['/referrals-list'])
  }

  onChangeCountry(event) {
    console.log(event, "nationality")
    if (!event) {
      this.nationalityValue = ''
      document.getElementById('country_of_origin').hidden = true
      this.formGroup.controls['countryOfOrigin'].reset();
    } else {
      this.nationalityValue = event;
      if (this.nationalityValue === "National") {
        document.getElementById('country_of_origin').hidden = true
      } else {
        document.getElementById('country_of_origin').hidden = false
      }

    }
  }

  onChangeFollowUp(event) {
    console.log(event, "nationality")
    if (!event) {
      this.followUpValue = ''
      document.getElementById('followupAreas').hidden = true
      document.getElementById('followupOrganization').hidden = true
      this.formGroup.controls['followupAreas'].reset();
      this.formGroup.controls['followupOrganization'].reset();
    } else {
      this.followUpValue = event;
      if (this.followUpValue === "No") {
        document.getElementById('followupAreas').hidden = true
        document.getElementById('followupOrganization').hidden = true
        this.formGroup.controls['followupAreas'].reset();
        this.formGroup.controls['followupOrganization'].reset();
      } else {
        document.getElementById('followupAreas').hidden = false
        document.getElementById('followupOrganization').hidden = false
      }

    }
  }

  deleteReferral() {
    if (confirm('Are you sure to delete this Referral?')) {
      console.log(
        this.referralsService.deleteCurrentReferral(this.route.snapshot.params.id).subscribe((result) => {
            console.warn(result, 'Referral has been deleted');
            this.alertService.warning(`Referral has been deleted`)
            this.router.navigate(['/referrals-list']);
          }, error => {
            this.alertService.error(`Failed to delete Referral`)
          }
        ));
    }
  }


}
