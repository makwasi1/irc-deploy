import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {ActivatedRoute, Router} from '@angular/router';
import {AlertService} from '../../../services/alert';
import {ProgramPartnersService} from '../../../services/program-partners.service';
import {CountriesService} from '../../../services/countries.service';
import {v4 as uuid} from 'uuid';
import {CellEdit, OnUpdateCell} from '../../../helpers/cell-edit';
import {UsersService} from '../../../services/users.service';
import {Validator} from '../../../helpers/validator';
import {GroupsService} from '../../../services/groups.service';

@Component({
  selector: 'app-edit-program-partners',
  templateUrl: './edit-program-partners.component.html',
  styleUrls: ['../program-partners.component.css']
})
export class EditProgramPartnersComponent implements OnInit, OnUpdateCell  {

  formGroup: FormGroup;
  submitted = false;
  inValidNumber = false;
  formData: any;
  programs = [];
  programPartnerId: any;
  countries = [];
  cities = [];
  organisationsInvolved: any = [];
  username: string;
  initialGroupName: any;
  groupName: any;
  groupParent: any;
  groupCreationFormGroup: FormGroup;
  constructor(private formBuilder: FormBuilder,
              private route: ActivatedRoute,
              private alertService: AlertService,
              private countriesService: CountriesService,
              private groupsService: GroupsService,
              private usersService: UsersService,
              private router: Router,
              private programPartnersService: ProgramPartnersService) { }

  ngOnInit(): void {
    this.countries = this.countriesService.getListOfAllCountries();
    this.programPartnerId = this.route.snapshot.params.id;
    this.programPartnersService.getCurrentProgramPartner(this.programPartnerId).subscribe((results: any) => {
      // tslint:disable-next-line:triple-equals
      if (results?.organisationsInvolved != '') { this.organisationsInvolved = JSON.parse(results?.organisationsInvolved); }
      this.formGroup = this.formBuilder.group({
        program: [results?.programId, [Validators.required]],
        cluster: [results?.cluster, [Validators.required]],
        organisation: [results?.organisation],
        physicalAddress: [results?.physicalAddress],
        organisationType: [results?.organisationType],
        nameContactPerson: [results?.nameContactPerson],
        telephoneContactPerson: [results?.telephoneContactPerson, [Validators.required]],
        emailContactPerson: [results?.emailContactPerson, [Validators.required, Validators.email]],
        country: [results?.country],
        city: [results?.city],
        dataCollector: [results?.dataCollector],
        organisationsInvolved: [results?.organisationsInvolved],
        areaOfOperation: [results?.areaOfOperation]
      });
      this.initialGroupName = this.formGroup.controls['cluster'].value;
      console.log(this.initialGroupName, 'initial group');
      this.usersService.getCurrentUser(results?.dataCollector).subscribe((data: any) => {
        this.username = data.names;
      });
    });
    this.programPartnersService.getPrograms().subscribe((data) => {
      this.programs = data;
    });
  }

  get f() {
    return this.formGroup.controls;
  }

  editProgramPartner() {
    console.log('Form Data',  this.formGroup.value);
    this.submitted = true;
    if (this.formGroup.invalid || this.inValidNumber) {
      console.log('Invalid');
      return;
    }
    this.formGroup.patchValue({organisationsInvolved: JSON.stringify(this.organisationsInvolved)});
    const programPartner = this.formGroup.value;
    console.log('Form Data', programPartner);
    this.programPartnersService.updateProgramPartner(this.programPartnerId, programPartner).subscribe(results => {
      this.alertService.success(`${programPartner.cluster} has been successfully updated `);
    }, error => {
      this.alertService.error(`${programPartner.cluster} could not be updated`);
    });
    if (this.initialGroupName !== this.formGroup.controls['cluster'].value) {
        console.log('method to update group');
      // creating a form group for creating a group
      /*this.groupCreationFormGroup = this.formBuilder.group({
        id: [null],
        name: [this.groupName],
        parentGroupId: [this.groupParent],
      });

    //  update the group
      this.groupsService.updateGroup(id, JSON.stringify(this.groupCreationFormGroup.value)).subscribe((result) => {
        console.warn(result, 'Group created Successfully');
        this.alertService.success(`Group has been updated`);
      }, error => {
        this.alertService.error('Failed to update the Group');
      });*/
    }

    if (this.formGroup.valid) {
      setTimeout(() => {
        this.formGroup.reset();
        this.submitted = false;
        this.router.navigate(['/programPartner']);
      }, 100);
    }
  }

  onSelectCountry(country) {
    this.countriesService.getCitiesForCountry(country).subscribe((response) => {
      this.cities = response.data;
    }, error => console.log(error));
  }

  addOrganization() {
    if (this.organisationsInvolved.length < 5) {
      const id = uuid();
      this.organisationsInvolved.push({id: id, name: '', contact: ''});
    }
  }

  validateNumber(value) {
    this.inValidNumber = Validator.telephoneNumber(value);
  }

  cellEditor(rowId, tdId, key: string, oldValue, type?: string) {
    new CellEdit().edit(rowId, tdId, oldValue, key, this.saveCellValue, type);
  }

  saveCellValue = (value: string, key: string, rowId): void => {
    if (value !== null && value !== undefined) {
      switch (key) {
        case 'orgName':
          if (this.organisationsInvolved.some(x => x.id === rowId)) {
            this.organisationsInvolved.forEach(function (item) {
              if (item.id === rowId) {
                item.name = value;
              }
            });
          }
          break;
        case 'orgContact':
          if (this.organisationsInvolved.some(x => x.id === rowId)) {
            this.organisationsInvolved.forEach(function (item) {
              if (item.id === rowId) {
                item.contact = value;
              }
            });
          }
          break;
      }
    }
  }

  cancel(): void {
    window.history.back();
  }

}
