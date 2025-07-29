import {Component, OnInit} from '@angular/core';
import {InputComponent} from '../../commons/input/input.component';
import {FormControl, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {AuthService} from '../auth.service';
import {TranslatePipe} from '@ngx-translate/core';
import {NzFormDirective} from 'ng-zorro-antd/form';
import {NzColDirective, NzRowDirective} from 'ng-zorro-antd/grid';
import {RouterLink} from '@angular/router';
import {NzButtonComponent} from 'ng-zorro-antd/button';
import {GUTTER_H, GUTTER_V} from '../../../utils/const';
import {NzCheckboxComponent} from 'ng-zorro-antd/checkbox';

@Component({
  selector: 'dq-login',
  imports: [
    InputComponent,
    ReactiveFormsModule,
    TranslatePipe,
    NzFormDirective,
    NzRowDirective,
    NzColDirective,
    RouterLink,
    NzButtonComponent,
    NzCheckboxComponent
  ],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent implements OnInit {
  formGroup: FormGroup
  isSubmitting = false;
  constructor(private authService: AuthService,) {
  }
  ngOnInit(): void {
    this.initForm();
  }
  initForm(){
    this.formGroup = new FormGroup({
      username: new FormControl('', [Validators.required]),
      password: new FormControl('', [Validators.required]),
      rememberMe: new FormControl(false),
    })
  }

  login(){
    this.isSubmitting = true;
    this.authService.login(this.formGroup.getRawValue()).subscribe((data) => {
      this.isSubmitting = false;
      console.log(data);
    })
  }

  protected readonly GUTTER_H = GUTTER_H;
  protected readonly GUTTER_V = GUTTER_V;
}
