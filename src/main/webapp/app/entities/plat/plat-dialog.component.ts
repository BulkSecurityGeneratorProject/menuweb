import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Plat } from './plat.model';
import { PlatPopupService } from './plat-popup.service';
import { PlatService } from './plat.service';
import { User, UserService } from '../../shared';
import { Tag, TagService } from '../tag';

@Component({
    selector: 'jhi-plat-dialog',
    templateUrl: './plat-dialog.component.html'
})
export class PlatDialogComponent implements OnInit {

    plat: Plat;
    isSaving: boolean;

    users: User[];

    tags: Tag[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private platService: PlatService,
        private userService: UserService,
        private tagService: TagService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.userService.query()
            .subscribe((res: HttpResponse<User[]>) => { this.users = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.tagService.query()
            .subscribe((res: HttpResponse<Tag[]>) => { this.tags = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.plat.id !== undefined) {
            this.subscribeToSaveResponse(
                this.platService.update(this.plat));
        } else {
            this.subscribeToSaveResponse(
                this.platService.create(this.plat));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Plat>>) {
        result.subscribe((res: HttpResponse<Plat>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Plat) {
        this.eventManager.broadcast({ name: 'platListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackUserById(index: number, item: User) {
        return item.id;
    }

    trackTagById(index: number, item: Tag) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}

@Component({
    selector: 'jhi-plat-popup',
    template: ''
})
export class PlatPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private platPopupService: PlatPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.platPopupService
                    .open(PlatDialogComponent as Component, params['id']);
            } else {
                this.platPopupService
                    .open(PlatDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
