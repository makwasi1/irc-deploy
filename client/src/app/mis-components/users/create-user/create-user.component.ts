import {Component, OnInit} from '@angular/core';
import {
  AbstractControl,
  FormArray,
  FormBuilder,
  FormControl,
  FormGroup,
  ValidationErrors,
  ValidatorFn,
  Validators
} from '@angular/forms';
import {Router} from '@angular/router';
import {AuthService} from '../../../services/auth.service';
import {UsersService} from '../../../services/users.service';
import {TagService} from '../../../services/tags';
import {AlertService} from '../../../services/alert';
import {UsernameValidator} from '../../../validators/username.validator';
import {RolesService} from '../../../services/roles.service';
import {GroupsService} from '../../../services/groups.service';
import {HttpParams} from '@angular/common/http';
import {ProgramPartnersService} from '../../../services/program-partners.service';


@Component({
  selector: 'app-user',
  templateUrl: './create-user.component.html',
  styleUrls: ['./create-user.component.scss']
})
export class CreateUserComponent implements OnInit {
   userTypeValue: any;
   partnerId: any;
   userGroupViaPartner = [];

    constructor(
    private userService: UsersService,
    private rolesService: RolesService,
    private groupsService: GroupsService,
    private tagsService: TagService,
    private alertService: AlertService,
    private authService: AuthService,
    private formBuilder: FormBuilder,
    private programPartnersService: ProgramPartnersService,
    private router: Router
  ) {
  }

  clicked = false;
  currentDashboards: any;
  formGroup: FormGroup;
  submitted = false;
  fieldTextType: boolean;
  userRoles: any;
  partnerUserRoles: any;
  staffUserRoles: any;
  userTypeFilled: any;
  partners: any;
  type_of_user = [
    {
      'name': 'Partner'
    },
    {
      'name': 'CRVPF Staff'
    }
  ];
  groups: any;

  get f() {
    return this.formGroup.controls;
  }

  ngOnInit(): void {
    this.rolesService.getRoles().subscribe(data => {
      this.userRoles = data;
      this.partnerUserRoles = data.filter(a => ['ROLE_PARTNER_DATA_VIEWER', 'ROLE_PARTNER_DATA_MANAGER'].includes(a.authority) );
      this.staffUserRoles = data.filter(a => a.authority.includes('Staff') ||
        !['ROLE_SUPER_ADMIN', 'ROLE_ADMIN', 'ROLE_DATA_COLLECTOR', 'ROLE_APPLICANT',
          'ROLE_PARTNER_DATA_VIEWER', 'ROLE_PARTNER_DATA_MANAGER'].includes(a.authority) );
      console.log(this.partnerUserRoles);
    }, error => {
      this.alertService.error('Failed to get Roles');
    });
    this.groupsService.getGroups().subscribe(data => {
      this.groups = data;
    }, error => {
      this.alertService.error('Failed to get Groups');
    });
    this.programPartnersService.getProgramPartners().subscribe((data) => {
      this.partners = data;
    });
    this.formGroup = this.formBuilder.group({
      password: ['', [Validators.required]],
      username: ['', [Validators.required, UsernameValidator.validateUsername(this.userService)]],
      names: ['', [Validators.required]],
      email: ['', [Validators.required, Validators.email]],
      role: [null],
      partner: [null],
      kengaGroup: [null],
      enabled: [true],
    });
  }

  toggleFieldTextType() {
    this.fieldTextType = !this.fieldTextType;
  }

  onChangePartner(event) {
    console.log(event);
    if (this.userTypeValue === 'Partner') {
      const params = new HttpParams()
        .set('partnerId', event);
      if (event) {
        this.userService.getUserGroup(params).subscribe((result) => {
          this.userGroupViaPartner.push(result.id);
          this.formGroup.controls['kengaGroup'].setValue(this.userGroupViaPartner);
          console.log(this.formGroup.value, 'The form group');
        });
      }

    }
  }

  createUser() {
    this.clicked = true;
    this.submitted = true;
    if (this.formGroup.invalid) {
      console.log('Invalid');
      return;
    }
    const formData = this.formGroup.value;
    console.log('formData', formData);

    this.userService.createUser(formData).subscribe((result) => {
      this.alertService.success(`User is created successfully`);

      console.log(formData.kengaGroup, 'Groups');
      console.log(formData.role, 'Role');

      formData.role.forEach((role) => {
        // insert the user's role in the user role table
        const userRoleData = new FormData();
        userRoleData.append('user', result.id);
        userRoleData.append('role', role);

        this.userService.createUserRole(userRoleData).subscribe(data => {
          console.log(data, 'User Role');
        }, error => {this.alertService.error('failed to create user role'); });
      });


      // insert the user's partner in the user partner table
      if (formData.partner) {
        const userPartnerData = new FormData();
        userPartnerData.append('user', result.id);
        userPartnerData.append('programPartner', formData.partner);

        this.userService.createUserPartner(userPartnerData).subscribe(data => {
          console.log(data, 'User Partner');
        }, error => {console.log('Did not create partner', error); });
      }

      // inserts user_id group_id pairs into the user group table
      if (formData.kengaGroup) {
        for (let i = 0; i < formData.kengaGroup.length; i++) {
          const userGroupData = new FormData();
          userGroupData.append('user', result.id);
          userGroupData.append('kengaGroup', formData.kengaGroup[i]);

          this.userService.createUserGroup(userGroupData).subscribe(data => {
            console.log(data , 'User group');
          }, error => {
            console.log('failed to create user groups Entries'); });
        }
      }

      this.router.navigate(['/users']);
    }, error => {
      this.alertService.error('Failed to Create the User');
    });
  }

  userType(event) {
    this.userTypeValue = event;
    if (event === 'Partner') {
      this.formGroup.controls['role'].reset();
      this.formGroup.controls['kengaGroup'].reset();
      this.formGroup.controls['partner'].reset();
      document.getElementById('role_partner').hidden = false;
      document.getElementById('role_staff').hidden = true;
      document.getElementById('partners').hidden = false;
      document.getElementById('groups').hidden = true;
    } else if (event === 'CRVPF Staff') {
      this.formGroup.controls['role'].reset();
      this.formGroup.controls['kengaGroup'].reset();
      this.formGroup.controls['partner'].reset();
      document.getElementById('role_staff').hidden = false;
      document.getElementById('role_partner').hidden = true;
      document.getElementById('groups').hidden = false;
      document.getElementById('partners').hidden = true;
    } else {
      document.getElementById('role_staff').hidden = true;
      document.getElementById('role_partner').hidden = true;
      document.getElementById('groups').hidden = true;
      document.getElementById('partners').hidden = true;
    }


  }

  resetForm() {
    this.formGroup.reset();
    this.clicked = false;
    this.submitted = false;
  }

}
