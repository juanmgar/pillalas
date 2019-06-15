import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAve } from 'app/shared/model/ave.model';
import { AveService } from './ave.service';

@Component({
  selector: 'jhi-ave-delete-dialog',
  templateUrl: './ave-delete-dialog.component.html'
})
export class AveDeleteDialogComponent {
  ave: IAve;

  constructor(protected aveService: AveService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.aveService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'aveListModification',
        content: 'Deleted an ave'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-ave-delete-popup',
  template: ''
})
export class AveDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ ave }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(AveDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.ave = ave;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/ave', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/ave', { outlets: { popup: null } }]);
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
