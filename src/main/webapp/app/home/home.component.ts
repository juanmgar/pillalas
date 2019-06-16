import { Component, OnInit } from '@angular/core';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { LoginModalService, AccountService, Account } from 'app/core';
import { Observatorio } from 'app/shared/model/observatorio.model';
import { Ave } from 'app/shared/model/ave.model';
import { AveService } from 'app/entities/ave';
import { ObservatorioService } from 'app/entities/observatorio';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'jhi-home',
  templateUrl: './home.component.html',
  styleUrls: ['home.scss']
})
export class HomeComponent implements OnInit {
  account: Account;
  modalRef: NgbModalRef;
  searchString: string;
  results: Ave[];
  resultsObs: Observatorio[];
  resultsVisible: boolean;

  constructor(
    private accountService: AccountService,
    private aveService: AveService,
    private observatorioService: ObservatorioService,
    private loginModalService: LoginModalService,
    private eventManager: JhiEventManager
  ) {}

  ngOnInit() {
    this.accountService.identity().then((account: Account) => {
      this.account = account;
    });
    this.resultsVisible = false;
    this.registerAuthenticationSuccess();
  }

  registerAuthenticationSuccess() {
    this.eventManager.subscribe('authenticationSuccess', message => {
      this.accountService.identity().then(account => {
        this.account = account;
      });
    });
  }

  isAuthenticated() {
    return this.accountService.isAuthenticated();
  }

  login() {
    this.modalRef = this.loginModalService.open();
  }

  autocomplete() {
    if (this.searchString == undefined || this.searchString == '') {
      this.results = [];
      this.resultsVisible = false;
    } else {
      this.aveService
        .findTextos(this.searchString)
        .subscribe((res: HttpResponse<Ave[]>) => (this.results = res.body), (res: HttpErrorResponse) => this.onError(res.message));
      this.observatorioService
        .findTextos(this.searchString)
        .subscribe(
          (res: HttpResponse<Observatorio[]>) => (this.resultsObs = res.body),
          (res: HttpErrorResponse) => this.onError(res.message)
        );
      this.resultsVisible = true;
    }
  }

  private onError(errorMessage: string) {
    // this.jhiAlertService.error(errorMessage, {}, 'top right');
  }
}
