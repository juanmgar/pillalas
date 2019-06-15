import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IObservatorio } from 'app/shared/model/observatorio.model';
import { ObservatorioService } from './observatorio.service';

@Component({
  selector: 'jhi-observatorio-delete-dialog',
  templateUrl: './observatorio-delete-dialog.component.html'
})
export class ObservatorioDeleteDialogComponent {
  observatorio: IObservatorio;

  constructor(
    protected observatorioService: ObservatorioService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.observatorioService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'observatorioListModification',
        content: 'Deleted an observatorio'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-observatorio-delete-popup',
  template: ''
})
export class ObservatorioDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ observatorio }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ObservatorioDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.observatorio = observatorio;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/observatorio', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/observatorio', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
