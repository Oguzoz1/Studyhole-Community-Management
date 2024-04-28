import { CUSTOM_ELEMENTS_SCHEMA, Component, Input, OnInit, ViewEncapsulation } from '@angular/core';
import { PostModel } from '../post-model';
import { AuthService } from '../../auth/shared/auth.service';
import { PostService } from '../post.service';
import { ToastrService } from 'ngx-toastr';
import { VotePayload } from './vote-payload';
import { VoteType } from './vote-type';
import { VoteService } from '../vote.service';
import { faArrowDown, faArrowUp } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';

@Component({
  selector: 'app-vote-button',
  standalone: true,
  imports: [FontAwesomeModule],
  templateUrl: './vote-button.component.html',
  styleUrl: './vote-button.component.css',
})
export class VoteButtonComponent implements OnInit {

  @Input() post?: PostModel;
  votePayload?: VotePayload;
  faArrowUp = faArrowUp;
  faArrowDown = faArrowDown;
  upvoteColor?: string;
  downvoteColor?: string;
  isLoggedIn?: boolean;

  constructor(private voteService: VoteService,
    private authService: AuthService,
    private postService: PostService, private toastr: ToastrService) {

    this.votePayload = {
      voteType: undefined,
      id: undefined
    }
    this.isLoggedIn = authService.isLoggedIn()
  }

  ngOnInit(): void {
    this.updateVoteDetails();
  }

  upvotePost() {
    this.votePayload!.voteType = VoteType.UPVOTE;
    this.votePost();
    this.downvoteColor = '';
  }

  downvotePost() {
    this.votePayload!.voteType = VoteType.DOWNVOTE;
    this.votePost();
    this.upvoteColor = '';
  }

  private votePost() {
    this.votePayload!.id = this.post?.postId;
    console.log(this.votePayload);
    this.voteService.votePost(this.votePayload!).subscribe({
      next: () => {
        this.updateVoteDetails()
      },
      error: () => {
        this.toastr.error("You can not vote on this!")
      }
    });
}

  private updateVoteDetails() {
    if (!this.post || !this.post.postId) {
      console.error("Post or post id do not exist");
      return; 
    }
  
    this.postService.getPost(this.post!.postId!).subscribe({
      next: (post) => {
        if (!this.authService.isLoggedIn()){
          console.log("User shall not vote");
        }
        this.post = post;
      },
      error: () => {
        console.error("failed to load details");
      }
    });
  }
}