class CommentsController < ApplicationController
 
  before_filter :authenticate, :only => :destroy

  def create
    @post = Post.find(params[:post_id])
    @comment = @post.comments.create(params[:comment])
    redirect_to post_path(@post)
  end

  def show
    @post = Post.find(params[:post_id])
    @comment = @post.comments.find(params[:id])
     respond_to do |format|
     	format.html
     	format.json { render  :json => @comment }
     end
  end

  def destroy
    @post = Post.find(params[:post_id])
    @comment = @post.comments.find(params[:id])
    @comment.destroy

    redirect_to post_path(@post)
  end
end
