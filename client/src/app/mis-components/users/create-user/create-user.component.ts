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
import {UsersService} from "../../../services/users.service";
import {TagService} from "../../../services/tags";
import {AlertService} from "../../../services/alert";
import {UsernameValidator} from "../../../Validators/username.validator";
import {RolesService} from "../../../services/roles.service";


@Component({
  selector: 'app-createppdauser',
  templateUrl: './create-user.component.html',
  styleUrls: ['./create-user.component.scss']
})
export class CreateUserComponent implements OnInit {

  constructor(
    private userService: UsersService,
    private rolesService: RolesService,
    private tagsService: TagService,
    private alertService: AlertService,
    private authService: AuthService,
    private formBuilder: FormBuilder,
    private router: Router
  ) {
  }

  clicked = false;
  currentDashboards: any
  formGroup: FormGroup
  submitted = false;
  fieldTextType: boolean;
  sex = [
    {
      'name': 'Male'
    },
    {
      'name': 'Female'
    }
  ];
  // represents the user roles
  user_Type: any;
  data_collector_Type = [
    {
      'name': 'Enumerator'
    },
    {
      'name': 'Field Staff'
    }
  ];
  groups = [
    {
      name: "Partner 4",
    },
    {
      name: "Partner 1",
    },
    {
      name: "Uganda",
    },
    {
      name: "CRVP-Staff",
    },
  ]

  get f() {
    return this.formGroup.controls;
  }

  ngOnInit(): void {
    this.rolesService.getRoles().subscribe( data =>{
      this.user_Type = data
    }, error => {this.alertService.error("Failed to get Roles")})
    this.formGroup = this.formBuilder.group({
      password: ['', [Validators.required]],
      username: ['', [Validators.required, UsernameValidator.validateUsername(this.userService)]],
      names: ['', [Validators.required]],
      email: [''],
      telephone: [''],
      role: [null],
      groups: [null],
      is_active: [false],
      data_collector_Type: [],
    });
  }

  toggleFieldTextType() {
    this.fieldTextType = !this.fieldTextType;
  }

  createUser() {
    this.clicked = true;
    this.submitted = true;
    if (this.formGroup.invalid) {
      console.log('Invalid');
      return;
    }
     const formData = this.formGroup.value;
    this.userService.createUser(formData).subscribe((result) => {
        this.alertService.success(`User is created successfully`);
        this.router.navigate(['/users']);
    },error => {this.alertService.error("Failed to Create the User")});
  }

  resetForm() {
    this.formGroup.reset()
    this.clicked =  false
    this.submitted = false
  }

}
