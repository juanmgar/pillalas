import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAvistamiento } from 'app/shared/model/avistamiento.model';
import { AvistamientoService } from './avistamiento.service';

@Component({
  selector: 'jhi-avistamiento-delete-dialog',
  templateUrl: './avistamiento-delete-dialog.component.html'
})
export class AvistamientoDeleteDialogComponent {
  avistamiento: IAvistamiento;

  constructor(
    protected avistamientoService: AvistamientoService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.avistamientoService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'avistamientoListModification',
        content: 'Deleted an avistamiento'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-avistamiento-delete-popup',
  template: ''
})
export class AvistamientoDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ avistamiento }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(AvistamientoDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.avistamiento = avistamiento;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/avistamiento', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/avistamiento', { outlets: { popup: null } }]);
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
